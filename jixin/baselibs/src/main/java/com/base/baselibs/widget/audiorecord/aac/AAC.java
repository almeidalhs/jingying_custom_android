package com.base.baselibs.widget.audiorecord.aac;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import com.base.baselibs.util.LogUtils;
import com.sinaapp.bashell.AacEncoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AAC implements Runnable {

	/**从默认的16000开始，若失败则会用其余的采样率*/
	private static int[] sampleRates = { 16000, 44100, 22050, 11025, 8000, 4000 };

	private boolean isStart = false;
	private FileOutputStream fos;
	private AudioRecord recordInstance;
	private int hAac;
	private AacEncoder aacEncoder;
	private int bufferSize,minBufferSize;
	private byte[] tempBuffer;
	private String fileName;
	private AACListener mAACListener;
	private int status = 0;
	private String systemTag;

	//创建一个handler，内部完成处理消息方法
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				isStart = false;
				Object result = msg.obj;
				status = AACStatus.Type_Faild;
				mAACListener.onRecordFaild(result.toString());
			} else if (msg.what == 0x124) {
				isStart = false;
				mAACListener.onRecordEnd(fileName);
			} else if (msg.what == 0x125) {
				isStart = false;
				mAACListener.onRecordCancel(fileName);
			} else if (msg.what == 0x126) {
				mAACListener.onRecording(volume);
			}
		}
	};

	public int getStatus() {
		return status;
	}

	public AAC(String fileName, String systemTag, AACListener mAACListener){
		this.fileName = fileName;
		this.mAACListener = mAACListener;
		this.systemTag = systemTag;
		try {
			fos = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			status = AACStatus.Type_Faild;
			mAACListener.onRecordFaild("创建失败,请查看路径是否存在!");
		}
	}

	/**设置默认采样率，默认16000*/
	public AAC sampleRateInHz(int sampleRateInHz){
		sampleRates[0] = sampleRateInHz;
		return this;
	}

	public void start(){
		status = AACStatus.Type_Default;
		isStart = true;
		Thread t = new Thread(this);
		t.start();
		mAACListener.onRecordStart();
	}

	public void end(){
		status = AACStatus.Type_End;
		isStart = false;
	}

	public void cancel(){
		status = AACStatus.Type_Cancel;
		isStart = false;
	}

	@Override
	public void run() {
		for (int i = 0; i < sampleRates.length; i++) {
			try {
				aacEncoder = new AacEncoder();
				hAac = aacEncoder.AACEncoderOpen(sampleRates[i], 1);
				minBufferSize = AudioRecord.getMinBufferSize(sampleRates[i], AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
				if(minBufferSize<2048)minBufferSize=2048;
				bufferSize = aacEncoder.inputSamples * 16 / 8;
				tempBuffer = new byte[minBufferSize];
				recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRates[i], AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize);//bufferSize);
				recordInstance.startRecording();
				break;
			} catch (Exception e) {
				e.printStackTrace();
				Message message = Message.obtain();
				message.what = 0x123;
				message.obj = "录音初始化失败, 请检查权限是否可用!";
				mHandler.sendMessage(message);
			} catch (UnsatisfiedLinkError e) {
				Message message = Message.obtain();
				message.what = 0x123;
				message.obj = "录音初始化失败, 请检查权限是否可用!";
				mHandler.sendMessage(message);
			}
		}

		if (recordInstance==null || recordInstance.getRecordingState()!=3) {
			isStart = false;
			Message message = Message.obtain();
			message.what = 0x123;
			message.obj = "录音失败, 请检查权限是否可用!";
			mHandler.sendMessage(message);
			return;
		}
		int num = 0;
		while (isStart) {
			status = AACStatus.Type_Ing;
			LogUtils.e("recordInstance.getRecordingState()："+recordInstance.getRecordingState());
			int bufferRead = recordInstance.read(tempBuffer, 0, minBufferSize);//bufferSize);
			LogUtils.e("bufferRead："+bufferRead);
			if (bufferRead > 0) {
				int n = minBufferSize/bufferSize;//算出需要循环几次取值
				for (int i = 0; i < n; i++) {
					byte[] temp = new byte[bufferSize];
					System.arraycopy(tempBuffer, i*bufferSize, temp, 0, bufferSize);
					byte[] ret = aacEncoder.AACEncoderEncode(hAac, temp, temp.length);
					try {
						fos.write(ret);
					} catch (IOException e) {
						e.printStackTrace();
						Message message = Message.obtain();
						message.what = 0x123;
						message.obj = "保存失败!";
						mHandler.sendMessage(message);
					} catch (Exception e) {
						Message message = Message.obtain();
						message.what = 0x123;
						message.obj = "录音失败!";
						mHandler.sendMessage(message);
					}
				}
			} else {
				isStart = false;
				Message message = Message.obtain();
				message.what = 0x123;
				message.obj = "录音失败, 请检查权限是否可用!";
				mHandler.sendMessage(message);
			}
			//计算出音量分贝值
			if (bufferRead>0) {
				long v = 0;
				for (int i = 0; i < tempBuffer.length; i++) {
					v += tempBuffer[i] * tempBuffer[i];//计算平方和
				}
				double mean = v/(double)bufferRead;//平方和/总长度，得到音量大小
				LogUtils.e("mean："+mean);

				volume = 10*Math.log10(mean);//转换公式
				LogUtils.e("volume分贝值："+volume);
				if (num>5 && mean<=0) {
					isStart = false;
					Message message = Message.obtain();
					message.what = 0x123;
					message.obj = "录音失败, 请检查权限是否可用!";
					mHandler.sendMessage(message);
				}
			}
			Message message = Message.obtain();
			message.what = 0x126;
			mHandler.sendMessage(message);
			num += 1 ;
		}
		try {
			recordInstance.stop();
			recordInstance.release();
			recordInstance = null;
		} catch (Exception e) {
			e.printStackTrace();
			status = AACStatus.Type_Faild;
			Message message = Message.obtain();
			message.what = 0x123;
			message.obj = "录音失败!";
			mHandler.sendMessage(message);
		}
		aacEncoder.AACEncoderClose(hAac);
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			status = AACStatus.Type_Faild;
			Message message = Message.obtain();
			message.what = 0x123;
			message.obj = "保存失败!";
			mHandler.sendMessage(message);
		}
		if (status == AACStatus.Type_End) {
			Message message = Message.obtain();
			message.what = 0x124;
			mHandler.sendMessage(message);
		} else if (status == AACStatus.Type_Cancel) {
			Message message = Message.obtain();
			message.what = 0x125;
			mHandler.sendMessage(message);
		}
	}

	private double volume = 0;
	/**实时获取音量大小*/
	public double getVolume(){
		return volume;
	}

	public interface AACListener{
		void onRecordStart();
		void onRecording(double volume);
		void onRecordEnd(String filePath);
		void onRecordCancel(String filePath);
		void onRecordFaild(String str);
	}

	public interface AACStatus{
		int Type_Default = 0;
		int Type_Ing = 1;
		int Type_End = 2;
		int Type_Cancel = 3;
		int Type_Faild = 4;
	}
}
