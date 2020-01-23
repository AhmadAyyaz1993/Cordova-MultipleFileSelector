package com.phonegap.plugin.MultipleFileSelector;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Formatter;
import java.text.DateFormat;
import android.provider.Settings;
import java.util.Currency;
import java.util.Locale;
import java.text.NumberFormat;
import android.net.Uri.Builder;
import org.json.JSONObject;
import org.json.JSONArray;
import android.content.Context;
import java.text.SimpleDateFormat;
import java.text.Format;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import com.red_folder.phonegap.plugin.backgroundservice.sample.DatabasePoolManager;
import com.tracer.joblogic.R;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.models.sort.SortingTypes;
import java.io.File;
import android.net.Uri;
public class MultipleFileSelectorPlugin extends CordovaPlugin {
 
	private static final String TAG = "MultipleFileSelector";
	private int maxFilesCount = 10;
	private String attachmentType = null;
 	private StringWriter logStringWriter = null;
	private PrintWriter logPrintWriter = null;

	private static final int CUSTOM_REQUEST_CODE = 532;
	private ArrayList<String> photoPaths = new ArrayList<>();
	private ArrayList<String> docPaths = new ArrayList<>();
	private CallbackContext callbackContext;

 @Override
 public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

	 Log.d(TAG, "Inside of MultipleFileSelector");
		if ("selectFiles".equals(action)) 
		{
			this.cordova.setActivityResultCallback(this);
			this.callbackContext = callbackContext;
			logStringWriter = new StringWriter();
			logPrintWriter = new PrintWriter(logStringWriter);

			JSONObject userData = args.getJSONObject(0);

			maxFilesCount = Integer.parseInt(userData.getString("MaxFilesCount"));
			attachmentType = userData.getString("AttachmentType");

			if (attachmentType.equals("image")){
				this.selectImages();
			} else if (attachmentType.equals("doc")) {
				this.selectFiles();
			}
			
			
			return true;
		}
		Log.d(TAG, "Called invalid action: "+action);
		return false;  
	 }


	 private boolean selectImages()
	 {
		try 
		{
			FilePickerBuilder.getInstance()
					.setMaxCount(maxFilesCount)
					.setActivityTitle("Please select media")
					.enableVideoPicker(true)
					.enableCameraSupport(false)
					.showGifs(true)
					.showFolderView(true)
					.enableSelectAll(false)
					.enableImagePicker(true)
					.sortDocumentsBy(SortingTypes.name)
					.pickPhoto(MultipleFileSelectorPlugin.this.cordova.getActivity(), CUSTOM_REQUEST_CODE);
			
		} 
		catch (Exception e) 
		{
			Log.d(TAG, "Exception occurred during the execution of the UpdateNotes: ".concat(e.getMessage()));
			return false;
		}
		finally 
		{
			Log.d(TAG, "Finally");
		}
		return true;
	 }

	 
	 private boolean selectFiles()
	 {
		try 
		{

			String[] zips = { ".zip", ".rar" };
			String[] pdfs = { ".pdf" };
			String[] docs = {".pdf", ".jpg", ".jpeg", ".png", ".xlsx", ".msg", ".docx", ".doc", ".xls", ".pages", ".txt", ".numbers", ".PAT TEST *", ".easycert", ".eml", ".zip", ".xlsm", ".bmp", ".html", ".xlsb", ".mp4", ".rtf", ".ods", ".tif", ".MOV", ".webloc", ".csv", ".webarchive", ".docm", ".xltx", ".vnd", ".EPS", ".rtm", ".pub", ".ppt", ".xps", ".pptx", ".psd", ".rar", ".vsd", ".gif", ".gsheet", ".dot", ".dwg", ".db", ".mpeg", ".ll", ".odt", ".pg", ".pps", ".ppsx", ".mp3", ".m4a", ".ogg", ".wav", ".m4v", ".wmv", ".avi", ".mpg", ".ogv", ".3gp", ".3g2"};

			FilePickerBuilder.getInstance()
					.setMaxCount(maxFilesCount)
					.setActivityTitle("Please select documents")
					.addFileSupport("ALL", docs)
					.addFileSupport("ZIP", zips)
					.addFileSupport("PDF", pdfs)
					.enableDocSupport(true)
					.enableSelectAll(true)
					.enableImagePicker(true)
					.sortDocumentsBy(SortingTypes.name)
					.pickFile(MultipleFileSelectorPlugin.this.cordova.getActivity());
		} 
		catch (Exception e) 
		{
			Log.d(TAG, "Exception occurred during the execution of the UpdateNotes: ".concat(e.getMessage()));
			return false;
		}
		finally 
		{
			Log.d(TAG, "Finally");

		}
		return true;
	 }

	@Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case CUSTOM_REQUEST_CODE:
				if (resultCode == Activity.RESULT_OK && data != null) {
					photoPaths = new ArrayList<>();

					ArrayList<String> temp = new ArrayList<>();
					temp.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));


					for (int i = 0 ; i < temp.size(); i++){
						photoPaths.add(Uri.fromFile(new File(temp.get(i).toString())).toString());
					}


					JSONArray jsonArray = new JSONArray(photoPaths);
					callbackContext.success(jsonArray);
				}
				break;

			case FilePickerConst.REQUEST_CODE_DOC:
				if (resultCode == Activity.RESULT_OK && data != null) {
					docPaths = new ArrayList<>();

					ArrayList<String> temp = new ArrayList<>();
					temp.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));


					for (int i = 0 ; i < temp.size(); i++){
						docPaths.add(Uri.fromFile(new File(temp.get(i).toString())).toString());
					}

					JSONArray jsonArray = new JSONArray(docPaths);
					callbackContext.success(jsonArray);
				}
				break;
		}

	}


	 

 }
