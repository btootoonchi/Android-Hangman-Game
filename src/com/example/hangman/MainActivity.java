package com.example.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

	RelativeLayout MainLayout;
	EditText etSubmitLetter, etSubmitWord;
	TextView tvMissed, tvWordLength, tvWordPuzzle;
	Button bSubmitLetter, bSubmitWord;
	String word;
	StringBuilder builder = new StringBuilder();
	int amount = 0;
	final int Line = 1001;
	final int Circle = 1002;
	static AssetManager mngr;
	DrawView drawViewHead, drawViewLeftEye, drawViewRightEye, drawViewBody, drawViewLeftArm, drawViewRightArm, drawViewLeftLeg, drawViewRightLeg;
	
	enum BodyParts
    {
        Head (1),
        Left_Eye (2),
        Right_Eye (3),
        Mouth (4),
        Left_Arm (5),
        Right_Arm (6),
        Body (7),
        Left_Leg (8),
        Right_leg (9);
        
        private int code;
        
        private BodyParts(int c) {
          code = c;
        }
        
        public int getCode() {
          return code;
        }
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MainLayout = (RelativeLayout) findViewById(R.id.bg);
		initialization();

		MakesLabels();
		bSubmitLetter.setOnClickListener(this);
		bSubmitWord.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void initialization() {
		drawViewHead = new DrawView(this, 343, 60, 20, 10, Circle, Color.RED);
		drawViewLeftEye = new DrawView(this, 338, 57, 3, 10, Circle, Color.BLUE);
		drawViewRightEye = new DrawView(this, 348, 57, 3, 10, Circle, Color.BLUE);
		drawViewBody = new DrawView(this, 343, 250, 343, 80, Line, Color.RED);
		drawViewLeftArm = new DrawView(this, 300, 180, 343, 100, Line, Color.RED);
		drawViewRightArm = new DrawView(this, 400, 180, 343, 100, Line, Color.RED);
		drawViewLeftLeg =  new DrawView(this, 300, 300, 343, 250, Line, Color.RED);
		drawViewRightLeg = new DrawView(this, 400, 300, 343, 250, Line, Color.RED); 
		mngr = getAssets();
		etSubmitLetter = (EditText) findViewById(R.id.editTextSubmitLetter);
		etSubmitWord = (EditText) findViewById(R.id.editTextSubmitWord);
		tvWordPuzzle = (TextView) findViewById(R.id.textViewWordPuzzle);
		tvMissed = (TextView) findViewById(R.id.tvMissed);
		tvWordLength = (TextView) findViewById(R.id.tvWordLength);
		bSubmitLetter = (Button) findViewById(R.id.bSubmitLetter);
		bSubmitWord = (Button) findViewById(R.id.bSubmitWord);
	}
	
	public void DrawBodyParts(BodyParts bp)
	{
		switch(bp)
		{
		case Head:
			MainLayout.addView(drawViewHead);
			break;
		case Left_Eye:
			MainLayout.addView(drawViewLeftEye);
            break;
        case Right_Eye:
        	MainLayout.addView(drawViewRightEye);
            break;
        case Mouth:
            break;
        case Body:
        	MainLayout.addView(drawViewBody);
            break;
        case Left_Arm:
        	MainLayout.addView(drawViewLeftArm);
            break;
        case Right_Arm:
        	MainLayout.addView(drawViewRightArm);
            break;
        case Left_Leg:
        	MainLayout.addView(drawViewLeftLeg);
            break;
        case Right_leg:
        	MainLayout.addView(drawViewRightLeg);
            break;
		}
	}
	
	/*private static String ReadbyUrlDirectly()
	{
		try
		{
			URL url = new URL("http://dictionary-thesaurus.com/wordlists/Adjectives%28929%29.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			List<String> words = new ArrayList<String>();
			String s;
		    int i = 0;
		    while ((s = reader.readLine()) != null)
		    {
		    	words.add(s);
		    	System.out.println(words.get(i));
		    	i++;
		    }    
		    
		    Random rdm = new Random();
		    return (words.get(rdm.nextInt(words.size())));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}*/
	
	/*private static String ReadbyAsyncTask()
	{
		new AsyncTask<String, Void, String>(){

			@Override
			protected String doInBackground(String... urlStr) {
				try{
				      URL url = new URL(urlStr[0]);
				      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				      List<String> words = new ArrayList<String>();
				      String s;
				      int i = 0;
				      while ((s = reader.readLine()) != null)
				      {
				    	  words.add(s);
				    	  System.out.println(words.get(i));
				    	  i++;
				      }
				      
				      Random rdm = new Random();
				      return (words.get(rdm.nextInt(words.size())));
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				return null;
			}
		}.execute("http://dictionary-thesaurus.com/wordlists/Adjectives%28929%29.txt");
		
		return null;
	}*/
	
	@SuppressLint("DefaultLocale")
	private static String ReadTextFile()
	{
		String line = null;
		
		InputStream is;
		Vector <String> assetVector = new Vector<String>();
		try {
			is = mngr.open("Wheel Of Fortune.txt");
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			while( null != ( line = br.readLine() ) )
			{
				line = line.toLowerCase();
				assetVector.add(line);
			}
			br.close();
			
			Random rdm = new Random();
			return assetVector.get(rdm.nextInt(assetVector.size()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	private static String GetRandomWord()
	{
		//return ReadbyUrlDirectly();
		//return ReadbyAsyncTask();
		return ReadTextFile();
	}
	
	public void MakesLabels()
	{
		word = GetRandomWord();
		
        for(int i = 0; i < (word.length() * 3); i++)
        {
        	if(i%3 == 0)
        		builder.append("-");
        	else
        		builder.append(" ");
        }
        tvWordPuzzle.setText(builder.toString());
        
        StringBuilder sb = new StringBuilder();
        sb.append("Word Length: ").append(word.length());
        tvWordLength.setText(sb);
	}

	@Override
	public void onClick(View view) {
		
		switch(view.getId())
		{
		case R.id.bSubmitLetter:
			//SubmitLetter();
			SubmitWord();
			break;
		case R.id.bSubmitWord:
			//SubmitWord();
			SubmitLetter();
			break;
		}

	}
	
	@SuppressLint("DefaultLocale")
	private void SubmitLetter()
	{
		if(etSubmitLetter.getText().toString().matches(""))
		{
			msbox("", "You did not enter a letter!", false);
			return;
		}
		
		char letter = etSubmitLetter.getText().charAt(0);
		if(!Character.isLetter(letter))
			msbox("Error", "Please submit only letters!", false);
		
		StringBuilder sb = new StringBuilder();
		sb.append(letter);
		if(word.contains(sb.toString().toLowerCase()))
		{
			char[] letters = word.toCharArray();
            for (int i = 0; i < letters.length; i++)
            {
                if (letters[i] == letter)
                    builder.replace(i * 3, (i + 1) * 3, sb + "  ");
                /*else
                	builder.replace(i * 3, (i + 1) * 3, "  -");*/
            }
            tvWordPuzzle.setText(builder.toString());
            
            if(builder.toString().contains("-"))
            {
            	etSubmitLetter.setText("");
            	return;
            }
            
            msbox("Congrats", "You have won!", true);
            ResetGame();
		}
		else
        {
			if((amount + 1) != 9)
				msbox("sorry", "The letter you guessed is not in the word!", false);
			String tvStringMissed =  (String)tvMissed.getText();
			tvMissed.setText(tvStringMissed + " " + sb + ",");
			etSubmitLetter.setText("");
			DrawBodyParts(BodyParts.values()[amount]);
            amount++;
            if (amount == 9)
            {
                msbox("", "Sorry, you lost! The letter was " + word, true);
                ResetGame();
            }
        }
	}
	
	private void SubmitWord()
	{
		/*String s = etSubmitWord.getText().toString();
		if(TextUtils.isEmpty(s))*/
		if(etSubmitWord.getText().toString().matches(""))
		{
			msbox("", "You did not enter a word!", false);
			return;
		}
		
		if(etSubmitWord.getText().toString() == word)
		{
			msbox("Congrats", "You have won!", true);
			ResetGame();
		}
        else
        {
            msbox("Sorry", "The word you guessed is wrong!", false);
            DrawBodyParts(BodyParts.values()[amount]);
            amount++;
            if (amount == 9)
            {
            	msbox("", "Sorry, you lost! The letter was " + word, true);
                ResetGame();
            }
        }
	}
	
	private void ResetGame()
    {
		MainLayout.removeView(drawViewHead);
		MainLayout.removeView(drawViewLeftEye);
		MainLayout.removeView(drawViewRightEye);
		MainLayout.removeView(drawViewBody);
		MainLayout.removeView(drawViewLeftArm);
		MainLayout.removeView(drawViewRightArm);
		MainLayout.removeView(drawViewLeftLeg);
		MainLayout.removeView(drawViewRightLeg);
		
		builder.delete(0, word.length() * 3);
        MakesLabels();
        tvMissed.setText("Missed: ");
        etSubmitLetter.setText("");
    }
	
	public void msbox(String title,String message, boolean action)
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);                      
	    dlgAlert.setTitle(title); 
	    dlgAlert.setMessage(message); 
	    if(action)
	    	dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    			return;//finish();
	    			}
	    		});
	    else
	    	dlgAlert.setPositiveButton("OK", null);
	    dlgAlert.setCancelable(true);
	    dlgAlert.create().show();
	    
	    //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
