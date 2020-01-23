var MultipleFileSelector = {

    MultipleFileSelector: function (attachmentType ,maxFilesCount , successCallback, failureCallback) {
        cordova.exec(successCallback, failureCallback, 'MultipleFileSelectorPlugin',
            'selectFiles', [{"AttachmentType":attachmentType, "MaxFilesCount":maxFilesCount}]);
    }
};

module.exports = MultipleFileSelector;