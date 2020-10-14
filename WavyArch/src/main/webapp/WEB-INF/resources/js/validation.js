function validatePerformerEditForm() {
    const isNameValid = validateInputName(60);
    const isDateValid = validateInputDate();
    const isImageValid = validateInputFileImage();
    const isDescValid = validateInputDescription(200);

    return isNameValid &&
        isDateValid &&
        isImageValid &&
        isDescValid;
};

function validatePlaylistEditCreateForm() {
    const isNameValid = validateInputName(100);
    const isImageValid = validateInputFileImage();

    return isNameValid && isImageValid;
};

function validatePlaylistAddAudioForm() {
    const isPlaylistNameValid = validateInputNameById(100, 'inputPlaylistName');
    const isAudioNameValid = validateInputNameById(60, 'inputAudioName'); 

    return isPlaylistNameValid && isAudioNameValid;
};

function validateAlbumEditForm() {
    const isNameValid = validateInputName(200);
    const isImageValid = validateInputFileImage();
    const isDateValid = validateInputDate();

    return isNameValid &&
        isImageValid &&
        isDateValid;
};

function validateAudioUploadEditForm(audioRequired) {
    const isNameValid = validateInputName(60);
    const isAudioValid = validateInputFileAudio(audioRequired);
    const isDateValid = validateInputDate();
    const isGenreValid = validateInputNameById(60, 'inputGenre', false);
    const isAlbumValid = validateInputNameById(200, 'inputAlbum', false);
    const isPerformersValid = validateInputNameById(1000, 'inputPerformers', false);
    const isAuthorsValid = validateInputNameById(1000, 'inputAuthors', false);
    const isTagsValid = validateInputNameById(400, 'inputTags', false);

    return isNameValid &&
        isAudioValid &&
        isDateValid &&
        isGenreValid &&
        isAlbumValid &&
        isPerformersValid &&
        isAuthorsValid &&
        isTagsValid;
};

function validateSearchRequest() {
    const isRequestValid = validateInputNameById(400, 'inputSearch', true);

    return isRequestValid;
};

function validateSignInForm() {
    const isLoginValid = validateInputNameById(20, 'inputLogin', true);
    const isPasswordValid = validateInputNameById(20, 'inputPassword', true);

    return isLoginValid && isPasswordValid;
}

function validateSignUpForm() {
    const isLoginValid = validateInputNameById(20, 'inputLogin', true);
    const isPasswordValid = validateInputNameById(20, 'inputPassword', true);
    const isPasswordRepValid = validateInputNameById(20, 'inputRepeatPassword', true);

    const isPasswordMatchValid = validatePasswordsMatch('inputPassword', 'inputRepeatPassword', 20);

    return isLoginValid &&
        isPasswordValid &&
        isPasswordRepValid &&
        isPasswordMatchValid;
}

function validateInputName(maxLen) {
    return validateInputNameById(maxLen, 'inputName');
};

function validateInputNameById(maxLen, idStr, required = true) {
    let idSelector = '#' + idStr;
    let inputField = jQuery(idSelector);
    let valueLen = inputField.val().length;
    let isValid;

    if (required) {
        isValid = valueLen > 0 && valueLen <= maxLen;
    } else {
        isValid = valueLen == 0 || valueLen <= maxLen;
    }

    updateValidationFeedback(inputField, isValid);

    if (!isValid) {
        if (required) {
            jQuery('#' + idStr + 'Feedback')
                .text('This field is required, max length is ' + maxLen + ' chars');
        } else {
            jQuery('#' + idStr + 'Feedback')
                .text('Max length is ' + maxLen + ' chars');
        }
    }

    return isValid;
};

function validateInputDate() {
    let inputField = jQuery('#inputCreationDate');
    let inputValue = inputField.val();
    let valueDate = new Date(inputValue);
    let currentDate = new Date();
    let isValid = inputValue.length == 0 || valueDate <= currentDate;

    updateValidationFeedback(inputField, isValid);

    if (!isValid) {
        jQuery('#inputCreationDateFeedback')
            .text('You cannot choose a future date');
    }

    return isValid;
};

function validateInputDescription(maxLen) {
    let inputField = jQuery('#inputDesc');
    let valueLen = inputField.val().length;
    let isValid = valueLen == 0 || valueLen <= maxLen;

    updateValidationFeedback(inputField, isValid);

    if (!isValid) {
        jQuery('#inputDescFeedback')
            .text('Max length is ' + maxLen + ' chars');
    }

    return isValid;
};

function validateInputFileImage(required = false) {
    let inputField = jQuery('#inputImageFile');
    let files = inputField[0].files;
    const fileTypes = ['image/png', 'image/gif', 'image/jpeg'];
    
    let isValid = false;

    if (files.length == 0) {
        if (required) {
            isValid = false;
        } else {
            isValid = true;
        }
    } else {
        let file = files[0];
        let fileType = file.type;
        let fileSize = file.size;

        if (fileTypes.includes(fileType) && fileSize <= 5000000) {
            isValid = true;
        }
    }

    if (!isValid) {
        if (required) {
            jQuery('#inputImageFileFeedback')
                .text('This field is required, you can load only png/jpg/jpeg/gif files, with max size 5MB');
        } else {
            jQuery('#inputImageFileFeedback')
                .text('You can load only png/jpg/jpeg/gif files, with max size 5MB');
        }
        
    }
    
    updateValidationFeedback(inputField, isValid);

    return isValid;
};

function validateInputFileAudio(required = false) {
    let inputField = jQuery('#inputAudioFile');
    let files = inputField[0].files;
    const fileTypes = ['audio/mpeg', 'audio/wav'];

    let isValid = false;

    if (files.length == 0) {
        if (required) {
            isValid = false;
        } else {
            isValid = true;
        }
    } else {
        let file = files[0];
        let fileType = file.type;
        let fileSize = file.size;

        if (fileTypes.includes(fileType) && fileSize <= 50000000) {
            isValid = true;
        }
    }

    if (!isValid) {
        if (required) {
            jQuery('#inputAudioFileFeedback')
                .text('This field is required, you can load only mp3/wav files, with max size 50MB');
        } else {
            jQuery('#inputAudioFileFeedback')
                .text('You can load only mp3/wav files, with max size 50MB');
        }
        
    }
    
    updateValidationFeedback(inputField, isValid);

    return isValid;
};

function validatePasswordsMatch(passFieldId, passRepeatFieldId) {
    let passSelector = '#' + passFieldId;
    let passRepSelector = '#' + passRepeatFieldId;
    let passInput = jQuery(passSelector);
    let passRepInput = jQuery(passRepSelector);
    let passValue = passInput.val();
    let passRepValue = passRepInput.val();
    let isValid = false;

    if (passValue.length > 0 && passValue.length == passRepValue.length) {
        if (passValue === passRepValue) {
            isValid = true;
        }
    }

    if (!isValid) {
        jQuery(passSelector + 'Feedback')
            .text('Passwords do not match!');
        jQuery(passRepSelector + 'Feedback')
            .text('Passwords do not match!');
    }

    updateValidationFeedback(passInput, isValid);
    updateValidationFeedback(passRepInput, isValid);

    return isValid;
}

function updateValidationFeedback(inputField, isValid) {
    if (isValid) {
        inputField.removeClass('is-invalid');
        inputField.addClass('is-valid');
    } else {
        inputField.removeClass('is-valid');
        inputField.addClass('is-invalid');
    }
};