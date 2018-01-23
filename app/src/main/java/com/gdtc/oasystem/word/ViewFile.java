/* Copyright 2010 Bian LiJun
2  *
3  * Licensed under the Apache License, Version 2.0 (the "License");
4  * you may not use this file except in compliance with the License.
5  * You may obtain a copy of the License at
6  *
7  * http://www.apache.org/licenses/LICENSE-2.0
8  *
9  * Unless required by applicable law or agreed to in writing, software
10  * distributed under the License is distributed on an "AS IS" BASIS,
11  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
12  * See the License for the specific language governing permissions and
13  * limitations under the License.
14  */

package com.gdtc.oasystem.word;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.gdtc.oasystem.R;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class ViewFile extends Activity{
	
	private String nameStr = null;
	
	private Range range = null;
	private HWPFDocument hwpf = null;
	
	private String htmlPath;
	private String picturePath;

	private WebView view;
	
	private List pictures;
	
	private TableIterator tableIterator;
	
	private int presentPicture = 0;
	
	private int screenWidth;
	
	private FileOutputStream output;
	
	private File myFile;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view);
		
		view = (WebView)findViewById(R.id.show);
		
		screenWidth = this.getWindowManager().getDefaultDisplay().getWidth() - 10;
		
		Intent intent = this.getIntent();
		nameStr = intent.getStringExtra("name");
	
		getRange();	
		
		makeFile();
		
		readAndWrite();
		
		view.loadUrl("content://com.android.htmlfileprovider/" + htmlPath);
		System.out.println("htmlPath" + htmlPath);
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "�����ļ�").setIcon(this.getResources().getDrawable(R.drawable.importdb));
		menu.add(0, 1, 1, "��������").setIcon(this.getResources().getDrawable(R.drawable.exportdb));
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case 0:
			showDialog(0);
			break;
		case 1:
			showDialog(1);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected Dialog onCreateDialog(int id){
		switch(id){
		case 0:
			return buildDialogProgram(ViewFile.this);
		case 1:
			return buildDialogAuthor(ViewFile.this);
		}
		return null;
	}
	
	private Dialog buildDialogAuthor(Context context){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(this.getResources().getDrawable(R.drawable.dslab));
		builder.setTitle(this.getResources().getString(R.string.aboutauthor));
		builder.setMessage(this.getResources().getString(R.string.author));
		builder.setPositiveButton(this.getResources().getString(R.string.gotit), new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
			
		});
		return builder.create();
		
	}
	
	private Dialog buildDialogProgram(Context context){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(this.getResources().getString(R.string.aboutprogram));
		builder.setIcon(this.getResources().getDrawable(R.drawable.importdb));
		String programInfo = this.getResources().getString(R.string.word) + hwpf.characterLength() + "\n";
		programInfo = programInfo + this.getResources().getString(R.string.paragrap) + range.numParagraphs() + "\n";
		programInfo = programInfo + this.getResources().getString(R.string.pictures) + pictures.size() + "\n";
		
		builder.setMessage(programInfo);
		builder.setPositiveButton(this.getResources().getString(R.string.gotit), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		return builder.create();
	}
	
	public void makeFile(){
		
		String sdStateString = android.os.Environment.getExternalStorageState();
    	
    	if(sdStateString.equals(android.os.Environment.MEDIA_MOUNTED)){
    		try{
    			File sdFile = android.os.Environment.getExternalStorageDirectory();
    			
    			String path = sdFile.getAbsolutePath() + File.separator + "xiao";
    			
    			String temp = path + File.separator + "my.html";
    			
    			File dirFile = new File(path);
    			if(!dirFile.exists()){
    				dirFile.mkdir();
    			}
    	   			
    			File myFile = new File(path + File.separator + "my.html");
    			
    			if(!myFile.exists()){
    				myFile.createNewFile();
    			}
    			
    			htmlPath = myFile.getAbsolutePath();
    		}
    		catch(Exception e){

    		}
    	}
	}
	
	/*������sdcard�ϴ���ͼƬ*/
	public void makePictureFile(){
		String sdString = android.os.Environment.getExternalStorageState();
		
		if(sdString.equals(android.os.Environment.MEDIA_MOUNTED)){
			try{
				File picFile = android.os.Environment.getExternalStorageDirectory();
				
				String picPath = picFile.getAbsolutePath() + File.separator + "xiao";
				
				File picDirFile = new File(picPath);
				
				if(!picDirFile.exists()){
					picDirFile.mkdir();
				}
				File pictureFile = new File(picPath + File.separator + presentPicture + ".jpg");
				
				if(!pictureFile.exists()){
					pictureFile.createNewFile();
				}
				
				picturePath = pictureFile.getAbsolutePath();
				
			}
			catch(Exception e){
				System.out.println("PictureFile Catch Exception");
			}
		}
	}
	
	public void onDestroy(){
		super.onDestroy();
	}
	/*��ȡword�е�����д��sdcard�ϵ�.html�ļ���*/
	public void readAndWrite(){
		
		try{
			myFile = new File(htmlPath);
			output = new FileOutputStream(myFile);
			String head = "<html><body>";
			String tagBegin = "<p>";
			String tagEnd = "</p>";
			
			
			output.write(head.getBytes());
			
			int numParagraphs = range.numParagraphs();
			
			for(int i = 0; i < numParagraphs; i++){
				Paragraph p = range.getParagraph(i);
				
				if(p.isInTable()){
					int temp = i;
					if(tableIterator.hasNext()){
						String tableBegin = "<table style=\"border-collapse:collapse\" border=1 bordercolor=\"black\">";
						String tableEnd = "</table>";
						String rowBegin = "<tr>";
						String rowEnd = "</tr>";
						String colBegin = "<td>";
						String colEnd = "</td>";
						
						Table table = tableIterator.next();
						
						output.write(tableBegin.getBytes());
						
						int rows = table.numRows();
						
						for( int r = 0; r < rows; r++){
							output.write(rowBegin.getBytes());
							TableRow row = table.getRow(r);
							int cols = row.numCells();
							int rowNumParagraphs = row.numParagraphs();
							int colsNumParagraphs = 0;
							for( int c = 0; c < cols; c++){
								output.write(colBegin.getBytes());
								TableCell cell = row.getCell(c);
								int max = temp + cell.numParagraphs();
								colsNumParagraphs = colsNumParagraphs + cell.numParagraphs();
								for(int cp = temp; cp < max; cp++){
									Paragraph p1 = range.getParagraph(cp);
									output.write(tagBegin.getBytes());
									writeParagraphContent(p1);
									output.write(tagEnd.getBytes());
									temp++;
								}
								output.write(colEnd.getBytes());
							}
							int max1 = temp + rowNumParagraphs;
							for(int m = temp + colsNumParagraphs; m < max1; m++){
								Paragraph p2 = range.getParagraph(m);
								temp++;
							}
							output.write(rowEnd.getBytes());
						}
						output.write(tableEnd.getBytes());
					}
					i = temp;
				}
				else{
					output.write(tagBegin.getBytes());
					writeParagraphContent(p);
					output.write(tagEnd.getBytes());
				}
			}

			String end = "</body></html>";
			output.write(end.getBytes());
			output.close();
		}
		catch(Exception e){
			System.out.println("readAndWrite Exception");
		}
	}
	/*�Զ������ʽ����html�ļ���д����*/
	public void writeParagraphContent(Paragraph paragraph){
		Paragraph p = paragraph;
		int pnumCharacterRuns = p.numCharacterRuns();
		
		for( int j = 0; j < pnumCharacterRuns; j++){
	
			CharacterRun run = p.getCharacterRun(j);
			
			if(run.getPicOffset() == 0 || run.getPicOffset() >= 1000){
				if(presentPicture < pictures.size()){
					writePicture();
				}
			}
			else{
				try{
					String text = run.text();
					if(text.length() >= 2 && pnumCharacterRuns < 2){
						output.write(text.getBytes());
					}
					else{
						int size = run.getFontSize();
						int color = run.getColor();
						String fontSizeBegin = "<font size=\"" + decideSize(size) + "\">";
						String fontColorBegin = "<font color=\"" + decideColor(color) + "\">";
						String fontEnd = "</font>";
						String boldBegin = "<b>";
						String boldEnd = "</b>";
						String islaBegin = "<i>";
						String islaEnd = "</i>";
	
						output.write(fontSizeBegin.getBytes());
						output.write(fontColorBegin.getBytes());
						
						if(run.isBold()){
							output.write(boldBegin.getBytes());
						}
						if(run.isItalic()){
							output.write(islaBegin.getBytes());
						}
						
						output.write(text.getBytes());
						
						if(run.isBold()){
							output.write(boldEnd.getBytes());
						}
						if(run.isItalic()){
							output.write(islaEnd.getBytes());
						}
						output.write(fontEnd.getBytes());
						output.write(fontEnd.getBytes());
					}
				}
				catch(Exception e){
					System.out.println("Write File Exception");
				}
			}
		}
	}
	/*��word�е�ͼƬд�뵽.jpg�ļ���*/
	public void writePicture(){
		Picture picture = (Picture)pictures.get(presentPicture);
		
		byte[] pictureBytes = picture.getContent();
		
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureBytes, 0, pictureBytes.length);
		
		makePictureFile();
		presentPicture++;
		
		File myPicture = new File(picturePath);
		
		try{
		
			FileOutputStream outputPicture = new FileOutputStream(myPicture);
		
			outputPicture.write(pictureBytes);
		
			outputPicture.close();
		}
		catch(Exception e){
			System.out.println("outputPicture Exception");
		}
		
		String imageString = "<img src=\"" + picturePath + "\"";
		
		if(bitmap.getWidth() > screenWidth){
			imageString = imageString + " " + "width=\"" + screenWidth + "\"";
		}
		imageString = imageString + ">";
		
		try{
			output.write(imageString.getBytes());
		}
		catch(Exception e){
			System.out.println("output Exception");
		}
	}
	/*����word��html�����ת��*/
	public int decideSize(int size){
		
		if(size >= 1 && size <= 8){
			return 1;
		}
		if(size >= 9 && size <= 11){
			return 2;
		}
		if(size >= 12 && size <= 14){
			return 3;
		}
		if(size >= 15 && size <= 19){
			return 4;
		}
		if(size >= 20 && size <= 29){
			return 5;
		}
		if(size >= 30 && size <= 39){
			return 6;
		}
		if(size >= 40){
			return 7;
		}
		return 3;
	}
	/*����word��html��ɫ��ת��*/
	private String decideColor(int a){
		int color = a;
		switch(color){
		case 1:
			return "#000000";
		case 2:
			return "#0000FF";
		case 3:
		case 4:
			return "#00FF00";
		case 5:
		case 6:
			return "#FF0000";
		case 7:
			return "#FFFF00";
		case 8:
			return "#FFFFFF";
		case 9:
			return "#CCCCCC";
		case 10:
		case 11:
			return "#00FF00";
		case 12:
			return "#080808";
		case 13:
		case 14: 
			return "#FFFF00";
		case 15: 
			return "#CCCCCC";
		case 16:
			return "#080808";
		default:
			return "#000000";
		}
	}
	
	private void getRange(){
		FileInputStream in = null;
		POIFSFileSystem pfs = null;
		try{
			in = new FileInputStream(nameStr);
			pfs = new POIFSFileSystem(in);
			hwpf = new HWPFDocument(pfs);
		}
		catch(Exception e){
			
		}
		range = hwpf.getRange();
		
		pictures = hwpf.getPicturesTable().getAllPictures();
		
		tableIterator = new TableIterator(range);
		
	}
	/*���������ذ�ť*/
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	   	if (keyCode == KeyEvent.KEYCODE_BACK) {
	   		Intent intent = new Intent();
	   		intent.setClass(ViewFile.this, FileDirList.class);
	   		startActivity(intent);
	   		this.finish();
	   	}
		return false;	
	 }
}
