/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zyp.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

class YcCardViewEclairMr1 implements YcCardViewImpl {

    final RectF sCornerRect = new RectF();

    @Override
    public void initStatic() {
        // Draws a round rect using 7 draw operations. This is faster than using
        // canvas.drawRoundRect before JBMR1 because API 11-16 used alpha mask textures to draw
        // shapes.
        YcRoundRectDrawableWithShadow.sRoundRectHelper =
                new YcRoundRectDrawableWithShadow.RoundRectHelper() {
            @Override
            public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                      Paint paint) {
                final float twoRadius = cornerRadius * 2;
                final float innerWidth = bounds.width() - twoRadius - 1;
                final float innerHeight = bounds.height() - twoRadius - 1;
                if (cornerRadius >= 1f) {
                    // increment corner radius to account for half pixels.
                    float roundedCornerRadius = cornerRadius + .5f;
                    sCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius,
                            roundedCornerRadius);
                    int saved = canvas.save();
                    canvas.translate(bounds.left + roundedCornerRadius,
                            bounds.top + roundedCornerRadius);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerWidth, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerHeight, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.translate(innerWidth, 0);
                    canvas.rotate(90);
                    canvas.drawArc(sCornerRect, 180, 90, true, paint);
                    canvas.restoreToCount(saved);
                    //draw top and bottom pieces
                    canvas.drawRect(bounds.left + roundedCornerRadius - 1f, bounds.top,
                            bounds.right - roundedCornerRadius + 1f,
                            bounds.top + roundedCornerRadius, paint);
                    canvas.drawRect(bounds.left + roundedCornerRadius - 1f,
                            bounds.bottom - roundedCornerRadius + 1f,
                            bounds.right - roundedCornerRadius + 1f, bounds.bottom, paint);
                }
                // center
                canvas.drawRect(bounds.left, bounds.top + Math.max(0, cornerRadius - 1f),
                        bounds.right, bounds.bottom - cornerRadius + 1f, paint);
            }
        };
    }

    @Override
    public void initialize(YcCardViewDelegate cardView, Context context, int backgroundColor,
                           float radius, float elevation, float maxElevation, int startShadowColor, int endShadowColor) {
        YcRoundRectDrawableWithShadow background = createBackground(context, backgroundColor, radius,
                elevation, maxElevation,startShadowColor,endShadowColor);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setCardBackground(background);
        updatePadding(cardView);
    }

    private YcRoundRectDrawableWithShadow createBackground(Context context, int backgroundColor,
                                                           float radius, float elevation, float maxElevation, int startShadowColor, int endShadowColor) {
        return new YcRoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius,
                elevation, maxElevation,startShadowColor,endShadowColor);
    }

    @Override
    public void updatePadding(YcCardViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        cardView.setMinWidthHeightInternal((int) Math.ceil(getMinWidth(cardView)),
                (int) Math.ceil(getMinHeight(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top,
                shadowPadding.right, shadowPadding.bottom);
    }

    @Override
    public void onCompatPaddingChanged(YcCardViewDelegate cardView) {
        // NO OP
    }

    @Override
    public void onPreventCornerOverlapChanged(YcCardViewDelegate cardView) {
        getShadowBackground(cardView).setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override
    public void setBackgroundColor(YcCardViewDelegate cardView, int color) {
        getShadowBackground(cardView).setColor(color);
    }

    @Override
    public void setRadius(YcCardViewDelegate cardView, float radius) {
        getShadowBackground(cardView).setCornerRadius(radius);
        updatePadding(cardView);
    }

    @Override
    public float getRadius(YcCardViewDelegate cardView) {
        return getShadowBackground(cardView).getCornerRadius();
    }

    @Override
    public void setElevation(YcCardViewDelegate cardView, float elevation) {
        getShadowBackground(cardView).setShadowSize(elevation);
    }

    @Override
    public float getElevation(YcCardViewDelegate cardView) {
        return getShadowBackground(cardView).getShadowSize();
    }

    @Override
    public void setMaxElevation(YcCardViewDelegate cardView, float maxElevation) {
        getShadowBackground(cardView).setMaxShadowSize(maxElevation);
        updatePadding(cardView);
    }

    @Override
    public float getMaxElevation(YcCardViewDelegate cardView) {
        return getShadowBackground(cardView).getMaxShadowSize();
    }

    @Override
    public float getMinWidth(YcCardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinWidth();
    }

    @Override
    public float getMinHeight(YcCardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinHeight();
    }

    private YcRoundRectDrawableWithShadow getShadowBackground(YcCardViewDelegate cardView) {
        return ((YcRoundRectDrawableWithShadow) cardView.getCardBackground());
    }
}
