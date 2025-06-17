package com.moneymong.moneymong.feature.sign.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.design_system.R
import com.moneymong.moneymong.design_system.theme.Blue04
import com.moneymong.moneymong.design_system.theme.Body2
import com.moneymong.moneymong.design_system.theme.Gray03
import com.moneymong.moneymong.design_system.theme.Gray08
import com.moneymong.moneymong.design_system.theme.Heading4
import com.moneymong.moneymong.design_system.theme.MMHorizontalSpacing
import com.moneymong.moneymong.feature.sign.dto.CarouselData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpOnboardingCarouselView(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(pageCount = { Int.MAX_VALUE })

        LaunchedEffect(pagerState) {
            var initPage = Int.MAX_VALUE / 2
            while (initPage % ONBOARDING_CAROUSEL_DATA_LIST.size != 0) {
                initPage++
            }

            pagerState.scrollToPage(initPage)
        }

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                state = pagerState,
            ) { pageIndex ->
                val actualIndex = pageIndex % ONBOARDING_CAROUSEL_DATA_LIST.size
                val carouselData = ONBOARDING_CAROUSEL_DATA_LIST[actualIndex]

                SignUpOnboardingCarouselItem(carouselData = carouselData)
            }
        }
        SignUpOnboardingDotIndicator(
            pageCount = ONBOARDING_CAROUSEL_DATA_LIST.size,
            currentIndex = pagerState.currentPage % ONBOARDING_CAROUSEL_DATA_LIST.size,
        )
    }
}

@Composable
fun SignUpOnboardingCarouselItem(
    modifier: Modifier = Modifier,
    carouselData: CarouselData,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(14.dp))
        Text(
            text = carouselData.title,
            style = Body2,
            color = Blue04,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = carouselData.description,
            style = Heading4,
            color = Gray08,
        )
        Spacer(Modifier.height(14.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = carouselData.imageRes),
            contentDescription = null
        )
    }
}

@Composable
fun SignUpOnboardingDotIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentIndex: Int,
) {
    Row(
        modifier = modifier.padding(vertical = 20.dp, horizontal = MMHorizontalSpacing),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentIndex
            val dotColor by animateColorAsState(
                targetValue = if (isSelected) Blue04 else Gray03,
                label = "DotColor"
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(
                        color = dotColor,
                        shape = CircleShape,
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpOnboardingCarouselPreview() {
    SignUpOnboardingCarouselView()
}

private val ONBOARDING_CAROUSEL_DATA_LIST = listOf(
    CarouselData(
        title = "장부 기록",
        description = "장부 기록을 가볍게 시작해보세요",
        imageRes = R.drawable.img_login_onboarding1,
    ),
    CarouselData(
        title = "장부 추가",
        description = "필요한 만큼 장부를 여러개 만들 수 있어요",
        imageRes = R.drawable.img_login_onboarding2,
    ),
    CarouselData(
        title = "친구 초대",
        description = "장부를 친구와 함께 관리할 수 있어요",
        imageRes = R.drawable.img_login_onboarding3,
    ),
)