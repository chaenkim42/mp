package com.example.myapplication.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.DragStartHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Location;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter implements PlaceItemTouchHelperCallback.OnItemMoveListener {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    public static final int EMPTY_CHILD = 2;
    public static boolean edit_flag = false;
    private List<Item> data;

    public interface OnStartDragListener{
        void onStartDrag(ListChildViewHolder listChildViewHolder);
    }

    private final OnStartDragListener startDragListener;
    private OnAdapterInteractionListener mListener;

    public ExpandableListAdapter(List<Item> data, Context context, OnStartDragListener onStartDragListener){
        this.data = data;
        mListener = (OnAdapterInteractionListener) context;
        startDragListener = onStartDragListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18*dp);
        int subItemPaddingTopAndBottom = (int) (5*dp);

        switch (viewType) {
            case HEADER: // HEADER 인 경우에 recyclerview_list_item.xml 을 생성
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_child, parent, false);
                ListChildViewHolder child = new ListChildViewHolder(view);

                return child;
            // CHILD 인 경우에는 TextView만 생성
//                TextView itemTextView = new TextView(context);
//                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
//                itemTextView.setTextColor(0x88000000);
//                itemTextView.setLayoutParams(
//                        new ViewGroup.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT));
//                return new RecyclerView.ViewHolder(itemTextView) {
//                };
            case EMPTY_CHILD:
                inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.scheduleform_recyclerview_empty_child, parent, false);
                ListEmptyChildViewHolder emptyChild = new ListEmptyChildViewHolder(view);
                return emptyChild;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item; // 지금 가르키는거
                itemController.header_title.setText(item.title);
                itemController.header_date.setText(item.subTitle);
                itemController.saveBtn.setVisibility(View.INVISIBLE);

                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.up_arrow);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.down_arrow);
                }
                itemController.toggleBtn_constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null) { //다 보여지고 있는 형태일때
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD
                                    || data.size() > pos + 1 && data.get(pos + 1).type == EMPTY_CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.down_arrow);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.up_arrow);
                            item.invisibleChildren = null;
                        }
                    }
                });

                // edit btn 눌렀을때 아이콘 보이기
                itemController.editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // edit btn 안보이게
                        itemController.editBtn.setVisibility(View.INVISIBLE);
                        // save btn 보이게
                        itemController.saveBtn.setVisibility(View.VISIBLE);
                    }
                });

                // save btn 눌렀을 때
                itemController.saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemController.saveBtn.setVisibility(View.INVISIBLE);
                        itemController.editBtn.setVisibility(View.VISIBLE);
                    }
                });
                break;
            case CHILD:
                final ListChildViewHolder controller = (ListChildViewHolder) holder;
                controller.title.setText(item.title);
                controller.circle.setText(String.valueOf(item.orderOfVisit));

                controller.edit_btn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                       if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                           startDragListener.onStartDrag(controller);
                           notifyDataSetChanged();
                       }
                        return false;
                    }
                });

                break;
            case EMPTY_CHILD:
                final ListEmptyChildViewHolder emptychild_controller = (ListEmptyChildViewHolder) holder;
                emptychild_controller.text_constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.addBtnClickedInAdapter(true);
                    }
                });
                emptychild_controller.plusCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.addBtnClickedInAdapter(true);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public TextView header_date;
        public ConstraintLayout toggleBtn_constraintLayout;
        public ImageView btn_expand_toggle;
        public Button budgetBtn;
        public Button editBtn, saveBtn;
        public Item refferalItem;

        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.recyclerviewheader_title);
            header_date = (TextView) itemView.findViewById(R.id.recyclerviewheader_date);
            toggleBtn_constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.recyclerviewheader_toggleBtn_constraintLayout);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.recyclerviewheader_btn_expand_toggle);
            budgetBtn = (Button) itemView.findViewById(R.id.recyclerviewheader_budgetBtn);
            editBtn = (Button) itemView.findViewById(R.id.recyclerviewheader_editBtn);
            saveBtn = itemView.findViewById(R.id.recyclerviewheader_saveBtn);
        }
    }

    public static class ListChildViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView circle;
        public ImageButton edit_btn;
        public DragStartHelper mDragHandler;

        public ListChildViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recyclerviewchild_location_title);
            circle = (TextView) itemView.findViewById(R.id.recyclerviewchild_circle);
            edit_btn = itemView.findViewById(R.id.edit_btn);

        }
    }

    private static class ListEmptyChildViewHolder extends  RecyclerView.ViewHolder{
        public TextView textView;
        public TextView plusCircle;
        public ConstraintLayout text_constraintLayout;
        public ImageButton edit_btn;

        public ListEmptyChildViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recyclerviewEmptychild_textView);
            plusCircle = (TextView) itemView.findViewById(R.id.recyclerviewEmptychild_circle);
            text_constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.recyclerviewEmptychild_text_constraintLayout);
            edit_btn = itemView.findViewById(R.id.edit_btn);
        }
    }

    public static class Item {
        public int type;
        public String title;
        public String subTitle;
        public int orderOfVisit;// for child item.
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String title, String subTitle) {
            if(type == HEADER){
                this.type = type;
                this.title = title; // ex. "Day 1"
                this.subTitle = subTitle; // ex "09/01 일"
            }
        }
        public Item(int type, Location location, int orderOfVisit){
            if(type == CHILD){
                this.type = type;
                this.title = location.getName(); // 여행장소 이름 ex. "여수엑스포"
                this.orderOfVisit= orderOfVisit;
            }
        }
        public Item(int type){
            if(type == EMPTY_CHILD){
                this.type = type;
            }
        }
    }

    public interface OnAdapterInteractionListener {
        void addBtnClickedInAdapter(boolean isClicked);
    }

}
