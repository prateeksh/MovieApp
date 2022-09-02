/*
package com.company.movieapp.utils

import android.content.ContentProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout


@Throws(NoSuchElementException::class)
private fun scaleImage(view: ImageView) {
    // Get bitmap from the the ImageView.
    var bitmap: Bitmap? = null
    bitmap = try {
        val drawing: Drawable = view.getDrawable()
        (drawing as BitmapDrawable).bitmap
    } catch (e: NullPointerException) {
        throw NoSuchElementException("No drawable on given view")
    } catch (e: ClassCastException) {
        // Check bitmap is Ion drawable
        Ion.with(view).getBitmap()
    }

    // Get current dimensions AND the desired bounding box
    var width = 0
    width = try {
        bitmap!!.width
    } catch (e: NullPointerException) {
        throw NoSuchElementException("Can't find bitmap on given view/drawable")
    }
    var height = bitmap.height
    val bounding = dpToPx(250)
    Log.i("Test", "original width = " + Integer.toString(width))
    Log.i("Test", "original height = " + Integer.toString(height))
    Log.i("Test", "bounding = " + Integer.toString(bounding))

    // Determine how much to scale: the dimension requiring less scaling is
    // closer to the its side. This way the image always stays inside your
    // bounding box AND either x/y axis touches it.
    val xScale = bounding.toFloat() / width
    val yScale = bounding.toFloat() / height
    val scale = if (xScale <= yScale) xScale else yScale
    Log.i("Test", "xScale = " + java.lang.Float.toString(xScale))
    Log.i("Test", "yScale = " + java.lang.Float.toString(yScale))
    Log.i("Test", "scale = " + java.lang.Float.toString(scale))

    // Create a matrix for the scaling and add the scaling data
    val matrix = Matrix()
    matrix.postScale(scale, scale)

    // Create a new bitmap and convert it to a format understood by the ImageView
    val scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    width = scaledBitmap.width // re-use
    height = scaledBitmap.height // re-use
    val result = BitmapDrawable(scaledBitmap)
    Log.i("Test", "scaled width = " + Integer.toString(width))
    Log.i("Test", "scaled height = " + Integer.toString(height))

    // Apply the scaled bitmap
    view.setImageDrawable(result)

    // Now change ImageView's dimensions to match the scaled image
    val params = view.getLayoutParams() as LinearLayout.LayoutParams
    params.width = width
    params.height = height
    view.setLayoutParams(params)
    Log.i("Test", "done")
}

private fun dpToPx(dp: Int): Int {
    val density: Float = ContentProvider.<Context>().getResources()
        .getDisplayMetrics().density
    return Math.round(dp.toFloat() * density)
}*/
