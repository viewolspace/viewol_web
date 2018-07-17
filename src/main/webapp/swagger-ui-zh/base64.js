(function($) {
	$.Base64 = function() {
		this.settings = $.extend(true, {}, $.Base64.defaults);
	};
	$.extend($.Base64, {
		defaults : {
			keys:["789_-ABCDEFGHIJKLMNOPQRSTUVWXYZ6abcdefghijklmnopqrstuvwxyz501234",
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-",
				"3456789_-ABCDEFGHIJKLMNOPQRSTUVWX2YZabcdefghijklmnopqr1stuvwxyz0",
				"-ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz9012345678",
				"_-ABCDEFGHIJKLMNOPQRSTUVWXYZ9abcdefghijklmnopqrstuvwxyz801234567",
				"9_-ABCDEFGHIJKLMNOPQRSTUVWXYZ8abcdefghijklmnopqrstuvwxyz70123456",
				"6789_-ABCDEFGHIJKLMNOPQRSTUVWXYZ5abcdefghijklmnopqrstuvwxyz40123",
				"89_-ABCDEFGHIJKLMNOPQRSTUVWXYZ7abcdefghijklmnopqrstuvwxyz6012345",
				"456789_-ABCDEFGHIJKLMNOPQRSTUVWXY3Zabcdefghijklmnopqrs2tuvwxyz01",
				"56789_-ABCDEFGHIJKLMNOPQRSTUVWXYZ4abcdefghijklmnopqrstuvwxyz3012"]
		},
		prototype : {
			getBytesFromString:function(str){
				var utf8 = unescape(encodeURIComponent(str));
				var arr = [];
				for (var i = 0; i < utf8.length; i++) {
					arr.push(utf8.charCodeAt(i));
				}
				return arr;
			},
			getStringFromBytes:function(arr){
				var i, str = '';
				for (i = 0; i < arr.length; i++) {
					str += '%' + ('0' + arr[i].toString(16)).slice(-2);
				}
				str = decodeURIComponent(str);
				return str;
			},
			encode: function (en_str) {
				var random_index = Math.floor(Math.random() * 10);
				var key = this.settings.keys[random_index];
				var de_str = "~" + random_index;

				var chr1, chr2, chr3;
				var en_byte = this.getBytesFromString(en_str),
					len = en_byte.length;
				var i = 0;
				while (i < len) {
					chr1 = en_byte[i++] & 0xff;
					if (i == len) {
						de_str += key.charAt(chr1 >> 2);
						de_str += key.charAt((chr1 & 0x3) << 4);
						break;
					}

					chr2 = en_byte[i++];
					if (i == len) {
						de_str += key.charAt(chr1 >> 2);
						de_str += key.charAt(((chr1 & 0x3) << 4) | ((chr2 & 0xF0) >> 4));
						de_str += key.charAt((chr2 & 0xF) << 2);
						break;
					}

					chr3 = en_byte[i++];
					de_str += key.charAt(chr1 >> 2);
					de_str += key.charAt(((chr1 & 0x3) << 4) | ((chr2 & 0xF0) >> 4));
					de_str += key.charAt(((chr2 & 0xF) << 2) | ((chr3 & 0xC0) >> 6));
					de_str += key.charAt(chr3 & 0x3F);
				}
				return de_str;
			},
			decode: function (de_str) {
				if (de_str.length <= 2 || de_str.charAt(0) != '~') {
					return de_str;
				}
				var key_index = de_str.substr(1, 1);
				var really_str = de_str.substr(2);

				var key = this.settings.keys[key_index];
				var len = really_str.length;
				var en_btyes = [];

				var chr1, chr2, chr3;
				var enc1, enc2, enc3, enc4;
				var i = 0;
				while (i < really_str.length) {
					enc1 = key.indexOf(really_str.charAt(i++));
					enc2 = key.indexOf(really_str.charAt(i++));
					enc3 = key.indexOf(really_str.charAt(i++));
					enc4 = key.indexOf(really_str.charAt(i++));

					chr1 = (enc1 << 2) | (enc2 >> 4);
					chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
					chr3 = ((enc3 & 3) << 6) | enc4;

					if (chr1 > 0) {
						en_btyes.push(chr1);
					}

					if (enc3 != 64 && chr2 > 0) {
						en_btyes.push(chr2);
					}

					if (enc4 != 64 && chr3 > 0) {
						en_btyes.push(chr3);
					}
				}
				return this.getStringFromBytes(en_btyes);
			}
		}
	});
})(jQuery);