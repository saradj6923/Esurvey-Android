/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey.question

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.Answer
import com.example.compose.jetsurvey.survey.SurveyActionResult
import com.example.compose.jetsurvey.survey.SurveyActionType
import com.example.compose.jetsurvey.theme.JetsurveyTheme

@Composable
fun PhotoQuestion(
    questionId: Int,
    answer: Answer.Action?,
    onAction: (Int, SurveyActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    val resource = if (answer != null) {
        Icons.Filled.SwapHoriz
    } else {
        Icons.Filled.AddAPhoto
    }
    OutlinedButton(
        onClick = { onAction(questionId, SurveyActionType.TAKE_PHOTO) },
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        Column {
            if (answer != null && answer.result is SurveyActionResult.Photo) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(answer.result.uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(96.dp)
                        .aspectRatio(4 / 3f)
                )
            } else {
                PhotoDefaultImage(modifier = Modifier.padding(horizontal = 86.dp, vertical = 74.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomCenter)
                    .padding(vertical = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = resource, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(
                        id = if (answer != null) {
                            R.string.retake_photo
                        } else {
                            R.string.add_photo
                        }
                    )
                )
            }
        }
    }
}

@Composable
private fun PhotoDefaultImage(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        R.drawable.ic_selfie_light
    } else {
        R.drawable.ic_selfie_dark
    }
    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PhotoQuestionPreview() {
    JetsurveyTheme {
        Surface {
            PhotoQuestion(questionId = 1, answer = null, onAction = { _, _ -> })
        }
    }
}
