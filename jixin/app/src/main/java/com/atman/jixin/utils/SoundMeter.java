package com.atman.jixin.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import com.base.baselibs.util.LogUtils;

import java.io.File;
import java.io.IOException;

public  class SoundMeter {
	static final private double EMA_FILTER = 0.6;

	private MediaRecorder mRecorder = null;
	private onRecordError monRecordError;
	private double mEMA = 0.0;
	private String path = Environment.getExternalStorageDirectory()+"/jiying/audio";

	public SoundMeter () {}

	public SoundMeter (onRecordError monRecordError) {
		this.monRecordError = monRecordError;
	}

	public String getPath() {
		return path;
	}

	public void start(String name) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return;
		}
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}

		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			mRecorder.setOutputFile(f.getPath()+"/"+name);
			try {
				mRecorder.prepare();
				mRecorder.start();
				
				mEMA = 0.0;
			} catch (IllegalStateException e) {
				monRecordError.onFaild(onRecordError.mIllegalStateException);
				System.out.print(e.getMessage());
			}  catch (RuntimeException e) {
				monRecordError.onFaild(onRecordError.mRuntimeException);
				System.out.print(e.getMessage());
			} catch (IOException e) {
				monRecordError.onFaild(onRecordError.mIOException);
				System.out.print(e.getMessage());
			}

		}
	}

	public void stop() {
		if (mRecorder != null) {
			try {
				mRecorder.stop();
				mRecorder.release();
				mRecorder = null;
			} catch (Exception e) {
				LogUtils.e("e:"+e.toString());
			}
		}
	}

	public void pause() {
		if (mRecorder != null) {
			mRecorder.stop();
		}
	}

	public void start() {
		if (mRecorder != null) {
			mRecorder.start();
		}
	}

	public double getAmplitude() {
		if (mRecorder != null)
			return (mRecorder.getMaxAmplitude() / 2700.0);
		else
			return 0;

	}

	public double getAmplitudeEMA() {
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}

	public interface onRecordError {
		int mIOException = 1;//输入输出异常
		int mRuntimeException = 2; //运行时异常
		int mIllegalStateException = 3; //非法状态异常
		void onFaild(int ExceptionId);
	}
}
