package com.example.smartmeal.adapter;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.smartmeal.MainActivity;
import com.example.smartmeal.R;
import com.example.smartmeal.fragment.MenuSuggestion;
import com.example.smartmeal.menu.MealMenu;

public class MenuAdapter extends BaseExpandableListAdapter {

	private final SparseArray<MealMenu>	groups;
	public LayoutInflater				inflater;
	public Activity						activity;
	public final MenuSuggestion			menuSuggestion;

	public MenuAdapter(Activity activity, MenuSuggestion menuSuggestion, SparseArray<MealMenu> groups) {
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
		if (childPosition == getChildrenCount(groupPosition) - 2 && !this.groups.get(groupPosition).isAccept()){
			text.setText("+");
			((Button) convertView.findViewById(R.id.button1)).setVisibility(View.INVISIBLE);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// choice list
					final ArrayList<Dish> dishes = MainActivity.smie.getDishes();
					Log.d("Number","" + dishes.size());
					// remove already chosen dish
					for (Dish d : MenuAdapter.this.groups.get(groupPosition).getMenu()){
						Log.d("Remove","" + d.getName());
						dishes.remove(d);
					}
					
					// dish name
					final ArrayList<String> values = new ArrayList<String>();
					for (Dish d : dishes)
						values.add(d.getName());

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.MAINACTIVITY, android.R.layout.simple_list_item_1, values);

					AlertDialog choices = new AlertDialog.Builder(activity)
							.setAdapter(adapter, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// info + submit + cancel
									final Dish dish = dishes.get(which);
									AlertDialog info = new AlertDialog.Builder(activity)
											.setTitle(dish.getName())
											.setMessage(dish.getQuantity() + " " + dish.getUnit()
													+ "\n" + dish.getEnergy() + " Kcal"
													+ "\n" + (int) dish.getProAmount() + " gram"
													+ "\n" + (int) dish.getGluAmount() + " gram "
													+ "\n" + (int) dish.getLipAmount() + " gram")
											.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {

												@Override
												public void onClick(DialogInterface dialog, int which) {
													// submit
													MenuAdapter.this.groups.get(groupPosition).add(dish);
													menuSuggestion.getMeal(groupPosition).addDish(dish.getId());
													menuSuggestion.loadMealMenu();
													dialog.dismiss();
												}
											})
											.setNegativeButton("Không thêm", new DialogInterface.OnClickListener() {

												@Override
												public void onClick(DialogInterface dialog, int which) {
													// cancel
													dialog.dismiss();
												}
											})
											.create();
									info.show();
								}
							})
							.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// cancel
									dialog.dismiss();
								}
							})
							.create();
					choices.show();
				}
			});
		}
		// last row: accept
		else if (childPosition == getChildrenCount(groupPosition) - 1 && !this.groups.get(groupPosition).isAccept()){
			if (this.groups.get(groupPosition).isAccept()) convertView.setVisibility(View.INVISIBLE);

			text.setText("!");
			((Button) convertView.findViewById(R.id.button1)).setVisibility(View.INVISIBLE);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = new AlertDialog.Builder(activity)
							.setMessage("Bạn chấp nhận thực đơn này")
							.setPositiveButton("Có", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									MenuAdapter.this.groups.get(groupPosition).accept();
									menuSuggestion.submitMeal(groupPosition);
									menuSuggestion.loadMealMenu();
									dialog.dismiss();
								}
							})
							.setNegativeButton("Không", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							})
							.create();
					alertDialog.show();
				}
			});
		}else{
			final Dish dish = (Dish) getChild(groupPosition, childPosition);

			text.setText(dish.getName());
			if (this.groups.get(groupPosition).isAccept()) ((Button) convertView.findViewById(R.id.button1)).setVisibility(View.INVISIBLE);
			else ((Button) convertView.findViewById(R.id.button1)).setVisibility(View.VISIBLE);

			// show info
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

			// remove this dish
			((Button) convertView.findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// alert dialog to confirm
					AlertDialog alertDialog = new AlertDialog.Builder(activity)
							.setTitle(dish.getName())
							.setMessage("Bạn chuẩn bị loại món này ra khỏi thực đơn")
							.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// remove
									MenuAdapter.this.groups.get(groupPosition).remove(dish);
									menuSuggestion.getMeal(groupPosition).removeDish(dish.getId());
									menuSuggestion.loadMealMenu();
									dialog.dismiss();
								}
							})
							.setNegativeButton("Không", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
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
		if (this.groups.get(groupPosition).isAccept()) return groups.get(groupPosition).getMenu().size();
		else return groups.get(groupPosition).getMenu().size() + 2;
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
