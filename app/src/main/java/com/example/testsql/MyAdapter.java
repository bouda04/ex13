package com.example.testsql;



import java.util.HashSet;

import com.example.testsql.data.DBContract.ItemEntry;
import com.example.testsql.data.DatabaseHelper;
import com.example.testsql.data.Item;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class MyAdapter extends ResourceCursorAdapter {
	private final static int MAX_ITEMS=16;
	public final static int MAX_NUMBERS = 100;
	public final static int SORT_BY_NUMS=1;
	public final static int SORT_BY_COLORS=2;
	public final static int SHUFFLE=3;
	private int currOrder= SORT_BY_NUMS;
	private DatabaseHelper dbh;
	private Context context;
	
	public MyAdapter(Context context, int layout) {
		super(context, layout, null, 0);
		this.dbh = DatabaseHelper.getInstance(context);
		this.context = context;
		Cursor cursor = this.dbh.getAllItems(currOrder);
		changeCursor(cursor);
	}

	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		TextView tv = (TextView) view.findViewById(R.id.tvNumber);
		int number = cursor.getInt(cursor.getColumnIndex(ItemEntry.KEY_NUMBER));
		view.setTag(cursor.getInt(cursor.getColumnIndex(ItemEntry.KEY_ID)));
		int color = cursor.getInt(cursor.getColumnIndex(ItemEntry.KEY_COLOR));
		GradientDrawable  background = (GradientDrawable) view.getBackground();
		background.setColor(color);
		if (number != -1){
			tv.setText(Integer.toString(number));
			view.setOnClickListener(null);
			view.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					dbh.deleteItem((Integer)v.getTag());
					changeCursor(dbh.getAllItems(currOrder));
					notifyDataSetChanged();
					return true;
				}
			});
		}
		else{
			tv.setText("...");
			if (cursor.getCount() < MAX_ITEMS)
				view.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						((MainActivity)context).openNewItemDialog();
					}
				});
			else
				view.setOnClickListener(null);
			view.setOnLongClickListener(null);
		}
			
	}
	
	public void addNewItem(Item item){
		int count = getCursor().getCount();
		this.dbh.addItem(item);
		changeCursor(this.dbh.getAllItems(currOrder));
		 
		notifyDataSetChanged();
	}
	
	public void updateItem(Item item){
		this.dbh.updateItem(item);
		changeCursor(this.dbh.getAllItems(currOrder));
		
		notifyDataSetChanged();
	}

	public HashSet<Integer>  getExistingNumbers(){
		return this.dbh.getExistingNumbers();
	}
	
	public void sortItems(int sortBy){
		this.currOrder= sortBy;
		changeCursor(this.dbh.getAllItems(sortBy));		
		notifyDataSetChanged();		
	}
}
