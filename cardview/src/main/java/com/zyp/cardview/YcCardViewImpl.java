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

/**
 * Interface for platform specific CardView implementations.
 */
interface YcCardViewImpl {
    void initialize(YcCardViewDelegate cardView, Context context, int backgroundColor, float radius,
                    float elevation, float maxElevation, int startShadowColor, int endShadowColor);

    void setRadius(YcCardViewDelegate cardView, float radius);

    float getRadius(YcCardViewDelegate cardView);

    void setElevation(YcCardViewDelegate cardView, float elevation);

    float getElevation(YcCardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(YcCardViewDelegate cardView, float maxElevation);

    float getMaxElevation(YcCardViewDelegate cardView);

    float getMinWidth(YcCardViewDelegate cardView);

    float getMinHeight(YcCardViewDelegate cardView);

    void updatePadding(YcCardViewDelegate cardView);

    void onCompatPaddingChanged(YcCardViewDelegate cardView);

    void onPreventCornerOverlapChanged(YcCardViewDelegate cardView);

    void setBackgroundColor(YcCardViewDelegate cardView, int color);
}
