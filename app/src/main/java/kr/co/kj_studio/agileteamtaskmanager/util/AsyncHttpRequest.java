package kr.co.kj_studio.agileteamtaskmanager.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class AsyncHttpRequest {

	public interface HttpResponseHandler {
		boolean onPrepare();		
		void onResponse(String response);
		void onFinish();
		void onCancelled();
	}



	public static void post(final Context context, final String url , final Map<String, Object> params, final boolean insert, final HttpResponseHandler handler) {
		AsyncTaskHandler async = new AsyncTaskHandler() {
			@Override
			public String doInBackground() {

				if (params == null)
					return HttpRequest.post(url).body();
				else
					return HttpRequest.post(url).form(params).body();
			}
		};

		new AsyncHttpRequestTask(context, async, insert, handler).execute();
	}

//	public static void postImageFiles(final Context context, final String url , final int chiefIndex , final Map<String, Object> params, final ArrayList<Bitmap> bitmapList, final HttpResponseHandler handler) {
//		AsyncTaskHandler async = new AsyncTaskHandler() {
//			@Override
//			public String doInBackground() {
//
//				HttpRequest request = HttpRequest.post(url);
//				for (Map.Entry<?, ?> entry : params.entrySet())
//				{
//					request.part(entry.getKey().toString(), entry.getValue().toString());
//				}
//
//				for (int i=0;i<bitmapList.size();i++)
//				{
//					Bitmap myBitmap = bitmapList.get(i);
//					ByteArrayOutputStream bao = new ByteArrayOutputStream();
//					myBitmap.compress(Bitmap.CompressFormat.JPEG, 70, bao);
//					byte [] ba = bao.toByteArray();
//					ByteArrayInputStream bs = new ByteArrayInputStream(ba);
//					SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
//					String dateTime = sdfNow.format(new Date(System.currentTimeMillis()));
//
//					String fileName = "cm_product_"+ContextUtil.getUserPhoneMum(context)+"_"+dateTime+".jpg";
//					if (i == chiefIndex) {
//						request.part("chiefImageName", fileName);
//					}
//					request.part("contentPhoto"+i, fileName,"image/jpg", bs);
//				}
//
//
//
//
//				return request.body();
//			}
//		};
//
//		new AsyncHttpRequestTask(context, async, true, handler).execute();
//	}

	public static void postProfileImageFile(final Context context, final String url , final Map<String, Object> params, final Bitmap bitmap, final String fileType, final HttpResponseHandler handler) {
		AsyncTaskHandler async = new AsyncTaskHandler() {
			@Override
			public String doInBackground() {

				HttpRequest request = HttpRequest.post(url);
				for (Map.Entry<?, ?> entry : params.entrySet())
				{
					request.part(entry.getKey().toString(), entry.getValue().toString());
				}

				if (bitmap != null)
				{
					Bitmap myBitmap = bitmap;
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					byte [] ba = bao.toByteArray();
					ByteArrayInputStream bs = new ByteArrayInputStream(ba);
					SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
					String dateTime = sdfNow.format(new Date(System.currentTimeMillis()));

					String fileName = "cm_"+fileType+"_"+"userid"+"_"+dateTime+".jpg";

					request.part("profileImg", fileName,"image/jpg", bs);
				}





				return request.body();
			}
		};

		new AsyncHttpRequestTask(context, async, true, handler).execute();
	}


	private interface AsyncTaskHandler {
		String doInBackground();
	}

	private static class AsyncHttpRequestTask extends AsyncTask<Void, Void, String> {

		protected Context mContext = null;
		private ProgressDialog mProgress = null;
		private AsyncTaskHandler mHandler = null;
		private HttpResponseHandler mResponseHandler = null;
		private boolean isInsert = false;

		public AsyncHttpRequestTask(Context context, AsyncTaskHandler handler, boolean isInsert, HttpResponseHandler responseHandler) {
			mContext = context;
			mHandler = handler;
			mResponseHandler = responseHandler;
			this.isInsert = isInsert;
		}

		@Override
		protected void onPreExecute() {

			Log.i("CHO", "onPreExecute");

			try {
				if (mContext != null && isInsert) {

					mProgress = new ProgressDialog(mContext);
					mProgress .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

					mProgress.setMessage( "잠시만 기다려주세요.." );
					mProgress.show();

					if (mResponseHandler != null)
						if (!mResponseHandler.onPrepare())
							cancel(true);

				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... arg) {
			return mHandler.doInBackground();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (mResponseHandler != null) {
				mResponseHandler.onResponse(result);
				mResponseHandler.onFinish();
			}
			if (mProgress != null)
			{
				mProgress.dismiss();

				//mProgress = null;

			}

		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (mResponseHandler != null)
				mResponseHandler.onCancelled();
			if (mProgress != null)
				mProgress.dismiss();			
		}
	}

}