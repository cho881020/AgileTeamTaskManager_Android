package kr.co.kj_studio.agileteamtaskmanager.util;


import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class ServerUtil {
	private static final String TAG = ServerUtil.class.getSimpleName();
	private static final String BASE_URL = "https://agile-jackiehoon-1.c9users.io/";


	public interface JsonResponseHandler {
		void onResponse(JSONObject json);
	}

	public interface StringResponseHandler {
		void onResponse(String response);
	}




	public static void ios_login(final Context context, final String fid, final JsonResponseHandler handler) {
		String url = BASE_URL+"mob/ios_login";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("fid", fid);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}


	public static void select_team(final Context context, final String fid, final JsonResponseHandler handler) {
		String url = BASE_URL+"mob/select_team";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", fid);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}


	public static void content(final Context context, final String team_id, final JsonResponseHandler handler) {
		String url = BASE_URL+"mob/content";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("team_id", team_id);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}


	public static void to_left(final Context context, final String team_id, String user_cont, String cont_id, final JsonResponseHandler handler) {
		String url = BASE_URL+"home/to_left";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("team_id", team_id);
		data.put("user_cont", user_cont);
		data.put("cont_id", cont_id);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}


	public static void to_right(final Context context, final String team_id, String user_cont, String cont_id, final JsonResponseHandler handler) {
		String url = BASE_URL+"home/to_right";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("team_id", team_id);
		data.put("user_cont", user_cont);
		data.put("cont_id", cont_id);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}

	public static void new_cont(final Context context, String user_id,  final String team_id, String user_content, final JsonResponseHandler handler) {
		String url = BASE_URL+"mob/new_cont";
		//		String registrationId = ContextUtil.getRegistrationId(context);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("team_id", team_id);
		data.put("user_content", user_content);
		data.put("user_id", user_id);

		AsyncHttpRequest.post(context, url,  data, false, new AsyncHttpRequest.HttpResponseHandler() {

			@Override
			public boolean onPrepare() {
				return true;
			}

			@Override
			public void onResponse(String response) {
//				Log.v("CHO", response);
				System.out.println(response);
				try {

					JSONObject json = new JSONObject(response);

					if (handler != null)
						handler.onResponse(json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onFinish() {

			}

			@Override
			public void onCancelled() {

			}

		});
	}
}
