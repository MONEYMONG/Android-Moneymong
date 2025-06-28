package com.moneymong.moneymong.feature.sign.state

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moneymong.moneymong.common.base.State
import com.moneymong.moneymong.feature.sign.util.AgencyType
import com.moneymong.moneymong.feature.sign.util.Grade
import com.moneymong.moneymong.model.sign.UniversitiesResponse

data class SignUpState(
    //screen
    val isSelected: Boolean? = null,
    val selectedUniv: String = "",
    val textValue: TextFieldValue = TextFieldValue(),
    val isEnabled: Boolean = false,
    val subTitleState: Boolean = false,
    val gradeInfor: Int = 0,
    val isInvited : Boolean = false,
    val buttonCornerShape : Dp = 10.dp,
    //view
    val isListVisible: Boolean = false,
    val isFilled: Boolean = false,
    val universityResponse: UniversitiesResponse? = null,
    val isUnivCreated : Boolean = false,
    val isAgencyCreated : Boolean = false,
    val editTextFocused : Boolean = false,
    val MDSSelected : Boolean = false,
    //item
    val isItemSelected : Boolean = false,
    val selectedGrade : Grade? = null,
    val agencyType : AgencyType? = null,
    val agencyName : TextFieldValue = TextFieldValue(),
    //error
    val visibleError : Boolean = false,
    val errorMessage : String = "",
    val visiblePopUpError : Boolean = false,
    val popUpErrorMessage : String = "",
    val isButtonVisible : Boolean = false,


    ) : State