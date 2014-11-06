package com.anjoyo.gamecenter.adapter;

import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.constant.GameInfo;
import com.anjoyo.gamecenter.constant.PublicData;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListViewItemAdapterRF extends BaseAdapter {

	private Context context;
	private List<GameInfo> list;

	public ListViewItemAdapterRF(Context context, List<GameInfo> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View
					.inflate(context, R.layout.listview_item_rf, null);
			holder.img = (ImageView) convertView
					.findViewById(R.id.img_listview_item_rf);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.txtTitle_listview_item_rf);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.ratingBar_listview_item_rf);
			holder.txtNum = (TextView) convertView
					.findViewById(R.id.txtNum_listview_item_rf);
			holder.txtDownload = (TextView) convertView
					.findViewById(R.id.txtDownload_listview_item_rf);
			holder.txtSize = (TextView) convertView
					.findViewById(R.id.txtSize_listview_item_rf);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Picasso.with(context)
				.load(PublicData.URL_MAIN + list.get(position).getIcon())
				.into(holder.img);
		holder.txtTitle.setText(list.get(position).getTitle());
		holder.ratingBar.setRating(list.get(position).getStar());
		holder.txtNum.setText(String.valueOf(list.get(position).getOnclick()));
		holder.txtSize.setText(list.get(position).getFilesize());

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView txtTitle;
		RatingBar ratingBar;
		TextView txtNum;
		TextView txtDownload;
		TextView txtSize;
	}

}
