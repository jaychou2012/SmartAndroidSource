package com.tandong.sa.bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapInfo {

	private Context c;

	public BitmapInfo(Context context) {

		this.c = context;
	}

	/**
	 * get Bitmap from local SDCard，no zoom
	 * 
	 * @param path
	 * 
	 * @return Bitmap
	 */
	public Bitmap getLocalBitmap(String path) {

		Bitmap bitmap = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}

	/**
	 * 对Bitmap图片对象进行缩放处理
	 * 
	 * @param bm
	 *            要处理的Bitmap对象
	 * @param newWidth
	 *            处理后的图片宽度
	 * @param newHeight
	 *            处理后的图片高度
	 * @return 新的缩放后的Bitmap对象
	 */
	public Bitmap getBitmapZoom(Bitmap bm, int newWidth, int newHeight) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		// bm.recycle();
		return newBm;
	}

	/**
	 * 返回当前Bitmap图片对象宽度和高度
	 * 
	 * @param bm
	 *            要获取尺寸的图片Bitmap对象
	 * @return 要返回的图片尺寸信息，例如返回格式为：当前图片宽度和高度分别为@700@500
	 */
	public String getBitmapSize(Bitmap bm) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		String s = "当前图片宽度和高度分别为@" + width + "@" + height;

		return s;
	}

	/**
	 * 将Bitmap保存到指定路径里，默认命名为当前时间，格式为PNG。注意路径后要加/ </br>
	 * 例如最终保存结果为：/sdcard/cacheImage/2013-08-06_14-57-52.png
	 * 
	 * @param bm
	 *            要保存的Bitmap对象
	 * @param savePath
	 *            要保存的文件路径，注意路径后要加/
	 * 
	 */
	public void saveBitmap(Bitmap bm, String savePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + sdf.format(new Date()) + ".png";
		FileOutputStream out;
		try {
			out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将Bitmap对象保存到指定的路径下，自己命名图片保存名称，格式为PNG格式
	 * 
	 * @param bm
	 *            要保存的Bitmap对象
	 * @param savePath
	 *            要保存的文件路径，注意路径后要加/
	 * @param saveName
	 *            保存后的图片名称
	 */
	public void saveBitmap(Bitmap bm, String savePath, String saveName) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + saveName + ".png";
		FileOutputStream out;
		try {
			out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 保存Bitmap到指定的文件夹，自己指定保存名称和保存类型（PNG,JPG,JPEG）
	 * 
	 * @param bm
	 *            要保存的Bitmap对象
	 * @param savePath
	 *            要保存的路径
	 * @param saveName
	 *            保存后的文件名
	 * @param imageType
	 *            要保存为的图片格式，只可输入：png或jpg或jpeg （不区分大小写）
	 */
	public void saveBitmap(Bitmap bm, String savePath, String saveName, String imageType) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + saveName + "." + imageType;
		FileOutputStream out;
		try {
			out = new FileOutputStream(fname);
			if (imageType.trim().toLowerCase().equalsIgnoreCase("png")) {
				bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			} else if (imageType.trim().toLowerCase().equalsIgnoreCase("jpeg")
					|| imageType.trim().toLowerCase().equalsIgnoreCase("jpg")) {
				bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			}
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 等比例缩放图片方法，第二个参数(0.1-1.0)为缩小;(1.1- )为放大
	 * 
	 * @param bm
	 *            要缩小的Bitmap对象
	 * @param scale
	 *            等比例缩小的尺寸，缩小范围（0.1 - 1.0）;放大范围(1.1 - )
	 * @return 返回缩放后的Bitmap对象
	 */
	public Bitmap getBitmapZoom(Bitmap bm, double scale) {
		// if (scale > 1.0) {
		// scale = 1.0;
		// Toast.makeText(c, "这个方法是获取缩小图片的方法，不可以放大图片,请讲参数设为小于或等于1.0", 3000)
		// .show();
		// }

		int width = bm.getWidth();
		int height = bm.getHeight();
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale((float) scale, (float) scale);
		Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		System.out.println("新图片的尺寸：" + newBm.getWidth() + "," + newBm.getHeight());
		return newBm;
	}

	/**
	 * 指定图片路径，对图片进行等比例缩小，指定要缩小到的图片宽度，图片高度等比例自动缩小
	 * 
	 * @param picPath
	 *            要处理的图片的路径
	 * @param height
	 *            要缩小到的图片宽度
	 * @return Bitmap对象
	 */
	public Bitmap getBitmapZoom(String picPath, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(picPath, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = (int) (options.outHeight / (float) height);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(picPath, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		return bitmap;
	}

	/**
	 * 设置保存图片质量的方法，默认保存为.jpg格式文件,减小图片大小，文件名为时间格式
	 * 
	 * @param bm
	 *            要处理的图片
	 * @param quality
	 *            要保存的图片质量（0-100）值越低压缩率越高，不压缩则设为100
	 */
	public void saveBitmapQuality(Bitmap bm, int quality, String savePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdir();
		}
		String fname = savePath + sdf.format(new Date()) + ".jpg";
		FileOutputStream out;
		try {
			out = new FileOutputStream(fname);
			bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
			System.out.println("file " + fname + "output done.");
			bm = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 将Bitmap对象转换为Drawable对象
	 * 
	 * @param bitmap
	 *            要转换的Bitmap
	 * @return 返回转换后的Drawable对象
	 */
	public Drawable bitmapToDrawable(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[]数组转为 Bitmap
	 * 
	 * @param b
	 *            数据数组
	 * @return 由byte[]数组转换为的Bitmap对象
	 */
	public Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Bitmap 转 byte[]
	 * 
	 * @param bm
	 *            bitmap对象
	 * @return 返回转换Bitmap转换后的数组数据
	 */
	public byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 70, baos);
		return baos.toByteArray();
	}
	
	

}
