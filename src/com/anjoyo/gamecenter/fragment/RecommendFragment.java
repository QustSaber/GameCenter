package com.anjoyo.gamecenter.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.ListViewItemAdapterRF;
import com.anjoyo.gamecenter.adapter.PagerAdapterRF;
import com.anjoyo.gamecenter.constant.GameInfo;
import com.anjoyo.gamecenter.constant.HotAdverInfo;
import com.anjoyo.gamecenter.constant.PublicData;
import com.anjoyo.gamecenter.customview.XListView;
import com.anjoyo.gamecenter.customview.XListView.IXListViewListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class RecommendFragment extends Fragment implements OnPageChangeListener,IXListViewListener {
	private View view;
	private ViewPager viewPager;
	private Context context;
	private List<View> list;
	private ImageView img1, img2, img3, img4;
	private ImageView imgCircle1, imgCircle2, imgCircle3, imgCircle4;
	private boolean flag = true;
	private int currentItem;
	private int currentPage = 1;
	private static final int SIGNAL0 = 0;
	private XListView mXListView;
	private List<GameInfo> gameInfoList;
	private ListViewItemAdapterRF listViewItemAdapterRF;


	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == SIGNAL0) {
				currentItem++;
				viewPager.setCurrentItem(currentItem % 4);
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = getActivity();
		view = inflater.inflate(R.layout.fragment_recommend, null);
		initViews();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		flag = true;
		new AutoSlide().start();
	}

	@Override
	public void onStop() {
		super.onStop();
		flag = false;
	}

	private void initViews() {

		viewPager = (ViewPager) view
				.findViewById(R.id.viewPager_fragment_recommend);

		img1 = (ImageView) View.inflate(context,
				R.layout.fragment_recommend_viewpager, null);
		img1.setBackgroundResource(R.drawable.focus_default);

		img2 = (ImageView) View.inflate(context,
				R.layout.fragment_recommend_viewpager, null);
		img2.setBackgroundResource(R.drawable.focus_default);

		img3 = (ImageView) View.inflate(context,
				R.layout.fragment_recommend_viewpager, null);
		img3.setBackgroundResource(R.drawable.focus_default);

		img4 = (ImageView) View.inflate(context,
				R.layout.fragment_recommend_viewpager, null);
		img4.setBackgroundResource(R.drawable.focus_default);

		imgCircle1 = (ImageView) view
				.findViewById(R.id.imgCircle1_fragment_recommend);
		imgCircle2 = (ImageView) view
				.findViewById(R.id.imgCircle2_fragment_recommend);
		imgCircle3 = (ImageView) view
				.findViewById(R.id.imgCircle3_fragment_recommend);
		imgCircle4 = (ImageView) view
				.findViewById(R.id.imgCircle4_fragment_recommend);

		list = new ArrayList<View>();
		list.add(img1);
		list.add(img2);
		list.add(img3);
		list.add(img4);

		viewPager.setAdapter(new PagerAdapterRF(list));
		viewPager.setOnPageChangeListener(this);
		initAdver();
		mXListView = (XListView) view
				.findViewById(R.id.listView_fragment_recommend);
		mXListView.setPullLoadEnable(true);
		mXListView.setPullRefreshEnable(false);
		gameInfoList = new ArrayList<GameInfo>();
	
		getRecommendInfo();
		listViewItemAdapterRF = new ListViewItemAdapterRF(context, gameInfoList);
		mXListView.setAdapter(listViewItemAdapterRF);
		mXListView.setXListViewListener(this);

	}

	private void initAdver() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, PublicData.URL_HOTADVER,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

						List<HotAdverInfo> list = new ArrayList<HotAdverInfo>();

						// 将获得的json数据解析
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg0.result);
							JSONArray jsonArray = jsonObject
									.getJSONArray("items");
							for (int i = 0; i < jsonArray.length(); i++) {
								HotAdverInfo info = new HotAdverInfo();
								JSONObject object = jsonArray.getJSONObject(i);
								info.setId(object.getInt("id"));
								info.setTitlepic(object.getString("titlepic"));
								info.setTitleurl(object.getString("titleurl"));
								info.setGameid(object.getInt("gameid"));
								list.add(info);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						Picasso.with(context)
								.load(PublicData.URL_MAIN
										+ list.get(0).getTitlepic()).into(img1);
						Picasso.with(context)
								.load(PublicData.URL_MAIN
										+ list.get(1).getTitlepic()).into(img2);
						Picasso.with(context)
								.load(PublicData.URL_MAIN
										+ list.get(2).getTitlepic()).into(img3);
						Picasso.with(context)
								.load(PublicData.URL_MAIN
										+ list.get(3).getTitlepic()).into(img4);

					}
				});
	}

	private void getRecommendInfo() {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, PublicData.URL_RECOMMEND + currentPage,
				new GetRequest());
	}

	private void changeImgState() {

		switch (currentItem % 4) {

		case 0:
			imgCircle4.setBackgroundResource(R.drawable.white_circle);
			imgCircle3.setBackgroundResource(R.drawable.black_circle);
			imgCircle2.setBackgroundResource(R.drawable.black_circle);
			imgCircle1.setBackgroundResource(R.drawable.black_circle);
			break;

		case 1:

			imgCircle4.setBackgroundResource(R.drawable.black_circle);
			imgCircle3.setBackgroundResource(R.drawable.white_circle);
			imgCircle2.setBackgroundResource(R.drawable.black_circle);
			imgCircle1.setBackgroundResource(R.drawable.black_circle);
			break;

		case 2:
			imgCircle4.setBackgroundResource(R.drawable.black_circle);
			imgCircle3.setBackgroundResource(R.drawable.black_circle);
			imgCircle2.setBackgroundResource(R.drawable.white_circle);
			imgCircle1.setBackgroundResource(R.drawable.black_circle);
			break;
		case 3:
			imgCircle4.setBackgroundResource(R.drawable.black_circle);
			imgCircle3.setBackgroundResource(R.drawable.black_circle);
			imgCircle2.setBackgroundResource(R.drawable.black_circle);
			imgCircle1.setBackgroundResource(R.drawable.white_circle);
			break;

		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		currentItem = arg0;
		changeImgState();
	}
	
	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		currentPage++;
		getRecommendInfo();
	}

	class AutoSlide extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				while (flag) {
					sleep(3000);
					handler.sendEmptyMessage(SIGNAL0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class GetRequest extends RequestCallBack<String> {

		@Override
		public void onFailure(HttpException arg0, String arg1) {

		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			try {
				JSONObject jsonObject = new JSONObject(arg0.result);
				JSONArray jsonArray = jsonObject.getJSONArray("items");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					GameInfo info = new GameInfo();
					info.setId(object.getInt("id"));
					info.setStar(object.getInt("star"));
					info.setPrice(object.getInt("price"));
					info.setTitle(object.getString("title"));
					info.setIcon(object.getString("icon"));
					info.setVersion(object.getString("version"));
					info.setFilesize(object.getString("filesize"));
					info.setOnclick(object.getInt("onclick"));
					info.setInfopfen(object.getInt("infopfen"));
					info.setInfopfennum(object.getInt("infopfennum"));
					info.setFlashurl(object.getString("flashurl"));
					info.setPachagename(object.getString("pachagename"));
					info.setTitlepic(object.getString("titlepic"));
					gameInfoList.add(info);
					listViewItemAdapterRF.notifyDataSetChanged();
					mXListView.stopLoadMore();

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	
}
