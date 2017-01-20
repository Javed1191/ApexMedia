package com.apexmediatechnologies.apexmedia;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import services.Application_Constants;
import services.FetchPath;
import services.MultipartUtility;
import services.ServiceHandler;
import services.Shared_Preferences_Class;
import services.Utility;


public class FragmentSendAudio extends Fragment
{


	View view;
	private Toolbar toolbar;
	private Utility utility;
	private String strPostJobByAudio = Application_Constants.Main_URL+"keyword=post_job_by_audio";
	String productId="",userId="",str_type="";
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".amr";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".amr";
	private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };

	private File audio_file;
	private ProgressDialog dialogProfile;
	private LinearLayout lay_start_recording,lay_stop_recording;
	private Button btnSubmit;
	private TextView tv_file_path,tv_timer;
	private CountDownTimer t;
	int cnt=0;
	private Chronometer myChronometer;
private ImageView img_start_recording;
	public FragmentSendAudio()
	{
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Enable the option menu for the Fragment
		setHasOptionsMenu(true);
		
		view=inflater.inflate(R.layout.fragment_send_audio,container, false);
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		utility = new Utility(getActivity());

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Post Job Audio");

		userId = Shared_Preferences_Class.readString(getActivity(), Shared_Preferences_Class.USER_ID, "");
		str_type = Shared_Preferences_Class.readString(getActivity(),Shared_Preferences_Class.USER_TYPE,"");
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		lay_start_recording = (LinearLayout) view.findViewById(R.id.lay_start_recording);
		lay_stop_recording = (LinearLayout) view.findViewById(R.id.lay_stop_recording);
		tv_file_path = (TextView) view.findViewById(R.id.tv_file_path);
		tv_timer = (TextView) view.findViewById(R.id.tv_timer);
		myChronometer = (Chronometer)view.findViewById(R.id.chronometer);
		img_start_recording = (ImageView) view.findViewById(R.id.img_start_recording);

		//setButtonHandlers();
		//enableButtons(false);
		//setFormatButtonCaption();

		img_start_recording.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "Start Recording", Toast.LENGTH_SHORT).show();
				img_start_recording.setVisibility(View.INVISIBLE);
				lay_stop_recording.setVisibility(View.VISIBLE);
				btnSubmit.setVisibility(View.GONE);
				myChronometer.start();

				startRecording();



			}
		});
		lay_stop_recording.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Stop Recording", Toast.LENGTH_SHORT).show();
				img_start_recording.setVisibility(View.VISIBLE);
				lay_stop_recording.setVisibility(View.INVISIBLE);
				myChronometer.stop();

				stopRecording();

			}
		});

		btnSubmit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					String str_path = getFilename();

					audio_file = new File(str_path);

					new PostJobAudio(userId).execute();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});


		return view;
	}

	private void setButtonHandlers() {
		((LinearLayout) view.findViewById(R.id.lay_start_recording)).setOnClickListener(btnClick);
		((LinearLayout) view.findViewById(R.id.lay_stop_recording)).setOnClickListener(btnClick);
		((Button) view.findViewById(R.id.btnFormat)).setOnClickListener(btnClick);
	}

	private void enableButton(int id, boolean isEnable) {
		((LinearLayout) view.findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.lay_start_recording, !isRecording);
		//enableButton(R.id.btnFormat, !isRecording);
		enableButton(R.id.lay_stop_recording, isRecording);
	}

	private void setFormatButtonCaption() {
		((Button) view.findViewById(R.id.btnFormat)).setText("Testing" + " (" + file_exts[currentFormat] + ")");
	}

	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
		return (file.getAbsolutePath() + "/" + "Test" + file_exts[currentFormat]);
	}

	private void startRecording()
	{



		// = getActivity().getFilesDir();
		//profilePic = new File(filesDir, "user_image.jpg");

		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());
		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);
		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



		//System.out.print(filesDir);

		/*Uri uri = Uri.parse(str_path);
		String str_file_name = uri.getLastPathSegment();

		System.out.print(str_file_name);
*/
	}

	private void stopRecording()
	{
		if (null != recorder)
		{
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;

			btnSubmit.setVisibility(View.VISIBLE);

			//tv_file_path.setText(getFilename());
			tv_file_path.setText("Audio Recorded Successfully : Post Job.amr ");



		}
	}

	private void displayFormatDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String formats[] = { "MPEG 4", "3GPP" };
		builder.setTitle("choose_format_title").setSingleChoiceItems(formats, currentFormat, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				currentFormat = which;
				setFormatButtonCaption();
				dialog.dismiss();
			}
		}).show();
	}


	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(getActivity(), "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(getActivity(), "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnStart: {
					Toast.makeText(getActivity(), "Start Recording", Toast.LENGTH_SHORT).show();
					enableButtons(true);
					startRecording();
					break;
				}
				case R.id.btnStop: {
					Toast.makeText(getActivity(), "Stop Recording", Toast.LENGTH_SHORT).show();
					enableButtons(false);
					stopRecording();
					break;
				}
				case R.id.btnFormat: {
					displayFormatDialog();
					break;
				}
			}
		}
	};






	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}


	class PostJobAudio extends AsyncTask<String, String, String>
	{
		String employerId;
		String str_status,json_responce,str_msg="",UserProfileImage="";

		public PostJobAudio(String employerId)
		{
			this.employerId = employerId;
		}

		@Override
		protected void onPreExecute()
		{
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialogProfile = new ProgressDialog(getActivity(),R.style.MyTheme);
			dialogProfile.setCancelable(false);
			dialogProfile.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
			dialogProfile.show();

		}

		@Override
		protected String doInBackground(String... params)
		{
			// TODO Auto-generated method stub

			String charset = "UTF-8";

			// File uploadFile1 = new File(str_image_path);
			try {
				MultipartUtility multipart = new MultipartUtility(strPostJobByAudio, charset);
				multipart.addHeaderField("User-Agent", "CodeJava");
				multipart.addHeaderField("Test-Header", "Header-Value");
				multipart.addFormField("employerId", employerId);
				multipart.addFilePart("audio_file", audio_file);

				json_responce= multipart.finish();


			} catch (IOException ex) {
				System.err.println(ex);
			}

			try {
				// str_json = sh.makeServiceCall(registration_url, ServiceHandler.POST, list_param);
				if(json_responce!=null)
				{

					JSONObject jobject = new JSONObject(json_responce);
					//JSONObject str_status_obj =jobject.getJSONObject("STATUS");
					str_msg= jobject.getString("Message");
					str_status = jobject.getString("Result");

				}
				else
				{
					// Toast.makeText(getApplicationContext(), "This may be server issue", Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO Auto-generated method stub
			dialogProfile.dismiss();

			super.onPostExecute(result);
			try
			{
				if(str_status.equalsIgnoreCase("true"))
				{
					Toast.makeText(getActivity(), str_msg,Toast.LENGTH_SHORT).show();

					FragmentEmployerDashboard fragment = new FragmentEmployerDashboard();
					android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
					fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("Dashboard");
					fragmentTransaction.commit();

				}
				else if(str_status.equalsIgnoreCase("false"))
				{

					Toast.makeText(getActivity(), str_msg, Toast.LENGTH_SHORT).show();

				}
				else
				{
					Toast.makeText(getActivity(), str_msg,Toast.LENGTH_SHORT).show();
				}


			}
			catch (Exception e)
			{
				e.printStackTrace();
			}


		}

	}

}
