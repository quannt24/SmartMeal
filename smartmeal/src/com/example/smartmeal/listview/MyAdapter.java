package com.example.smartmeal.listview;

import vn.hust.smie.Dish;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.smartmeal.R;
import com.example.smartmeal.fragment.MenuSuggestion;

public class MyAdapter extends BaseExpandableListAdapter {

	private final SparseArray<MealMenu>	groups;
	public LayoutInflater				inflater;
	public Activity						activity;
	public final MenuSuggestion menuSuggestion;
	
	public MyAdapter(Activity activity, MenuSuggestion menuSuggestion, SparseArray<MealMenu> groups) {
		this.activity = activity;
		this.groups = groups;
		this.menuSuggestion = menuSuggestion;
		inflater = activity.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getMenu().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		TextView text = null;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.row, null);
		}
		text = (TextView) convertView.findViewById(R.id.textView1);

		// before last row: add new meal
		Log.d("Out", "" + getChildrenCount(groupPosition));
		if (childPosition == getChildrenCount(groupPosition) - 2){
			text.setText("+");
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO choice list
				}
			});
			((Button) convertView.findViewById(R.id.button1)).setVisibility(View.INVISIBLE);
		}
		// last row: accept
		else if (isLastChild){
			text.setText("!");
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO submit
				}
			});
			((Button) convertView.findViewById(R.id.button1)).setVisibility(View.INVISIBLE);
		}else{
			final Dish dish = (Dish) getChild(groupPosition, childPosition);

			text.setText(dish.getName());
			// TODO show info
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = new AlertDialog.Builder(activity)
							.setTitle(dish.getName())
							.setMessage(dish.getQuantity() + " " + dish.getUnit()
									+ "\n" + dish.getEnergy() + " Kcal"
									+ "\n" + (int) dish.getProAmount() + " gram"
									+ "\n" + (int) dish.getGluAmount() + " gram "
									+ "\n" + (int) dish.getLipAmount() + " gram")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							})
							.create();
					alertDialog.show();
				}
			});

			// TODO remove this dish
			((Button) convertView.findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO alert dialog to confirm
					AlertDialog alertDialog = new AlertDialog.Builder(activity)
							.setTitle(dish.getName())
							.setMessage("UIAGSKUYGAK")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									groups.get(groupPosition).remove(dish);
									MenuSuggestion.getMeal(groupPosition).removeDish(dish.getId());
									MenuSuggestion.loadMealMenu(menuSuggestion);
									dialog.dismiss();
								}
							})
							.create();
					alertDialog.show();
				}
			});
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getMenu().size() + 2;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = inflater.inflate(R.layout.group, null);
		}
		MealMenu group = (MealMenu) getGroup(groupPosition);
		((CheckedTextView) convertView).setText(group.string);
		((CheckedTextView) convertView).setChecked(isExpanded);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
