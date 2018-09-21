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
import android.util.Log;
import android.view.View;

class YcCardViewApi21 implements YcCardViewImpl {

    @Override
    public void initialize(YcCardViewDelegate cardView, Context context, int backgroundColor,
                           float radius, float elevation, float maxElevation, int startShadowColor, int endShadowColor) {
        Log.e("AAA","CardViewApi21");
        final YcRoundRectDrawable background = new YcRoundRectDrawable(backgroundColor, radius);
        cardView.setCardBackground(background);

        View view = cardView.getCardView();
        view.setClipToOutline(true);
        view.setElevation(elevation);
        setMaxElevation(cardView, maxElevation);
    }

    @Override
    public void setRadius(YcCardViewDelegate cardView, float radius) {
        getCardBackground(cardView).setRadius(radius);
    }

    @Override
    public void initStatic() {
    }

    @Override
    public void setMaxElevation(YcCardViewDelegate cardView, float maxElevation) {
        getCardBackground(cardView).setPadding(maxElevation,
                cardView.getUseCompatPadding(), cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override
    public float getMaxElevation(YcCardViewDelegate cardView) {
        return getCardBackground(cardView).getPadding();
    }

    @Override
    public float getMinWidth(YcCardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getMinHeight(YcCardViewDelegate cardView) {
        return getRadius(cardView) * 2;
    }

    @Override
    public float getRadius(YcCardViewDelegate cardView) {
        return getCardBackground(cardView).getRadius();
    }

    @Override
    public void setElevation(YcCardViewDelegate cardView, float elevation) {
        cardView.getCardView().setElevation(elevation);
    }

    @Override
    public float getElevation(YcCardViewDelegate cardView) {
        return cardView.getCardView().getElevation();
    }

    @Override
    public void updatePadding(YcCardViewDelegate cardView) {
        if (!cardView.getUseCompatPadding()) {
            cardView.setShadowPadding(0, 0, 0, 0);
            return;
        }
        float elevation = getMaxElevation(cardView);
        final float radius = getRadius(cardView);
        int hPadding = (int) Math.ceil(YcRoundRectDrawableWithShadow
                .calculateHorizontalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        int vPadding = (int) Math.ceil(YcRoundRectDrawableWithShadow
                .calculateVerticalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
        cardView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
    }

    @Override
    public void onCompatPaddingChanged(YcCardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void onPreventCornerOverlapChanged(YcCardViewDelegate cardView) {
        setMaxElevation(cardView, getMaxElevation(cardView));
    }

    @Override
    public void setBackgroundColor(YcCardViewDelegate cardView, int color) {
        getCardBackground(cardView).setColor(color);
    }

    private YcRoundRectDrawable getCardBackground(YcCardViewDelegate cardView) {
        return ((YcRoundRectDrawable) cardView.getCardBackground());
    }
}