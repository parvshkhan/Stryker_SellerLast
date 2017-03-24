package com.app.utills;

import com.squareup.picasso.Transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class CircleTransform implements Transformation {
 
 public Bitmap transform(Bitmap source) {
  int size = Math.min(source.getWidth(), source.getHeight());

  int x = (source.getWidth() - size) / 2;
  int y = (source.getHeight() - size) / 2;

  Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
  if (squaredBitmap != source) {
   source.recycle();
  }

  Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

  Canvas canvas = new Canvas(bitmap);
  Paint paint = new Paint();
  BitmapShader shader = new BitmapShader(squaredBitmap,
    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
  paint.setShader(shader);
  paint.setAntiAlias(true);

  float r = size / 2f;
  canvas.drawCircle(r, r, r, paint);

  Paint paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG); // create
                // anti-aliased
                // Paint
  paintStroke.setStyle(Paint.Style.STROKE); // define paint style as
             // Stroke
  paintStroke.setStrokeWidth(4f); // set stroke widht to 1 px
  paintStroke.setColor(Color.parseColor("#ffffff"));
  final RectF rect = new RectF(0, 0, size, size - 2);// set color
  canvas.drawArc(rect, 0f, 3600f, true, paintStroke); // draw arch from 0
               // to 360 degrees
               // (i. e. closed
               // circle)

  squaredBitmap.recycle();
  return bitmap;
 }

 
 public String key() {
  return "circle";
 }
}