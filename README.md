Multiple file selector plugin for Cordova / PhoneGap
======================================================

## Installation
cordova plugin add com.phonegap.plugin.MultipleFileSelector



## Usage

Example Usage: 

```js
//for images
window.MultipleFileSelector.MultipleFileSelector("image", 10, function (res) {
           ...
        },
        function (err) {
           ...
        });
```

```js
//for files
window.MultipleFileSelector.MultipleFileSelector("doc", 10, function (res) {
            ...
        },
        function (err) {
           ...
        });
```

