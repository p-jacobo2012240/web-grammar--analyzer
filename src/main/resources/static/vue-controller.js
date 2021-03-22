// https://developer.mozilla.org/es/docs/Web/API/Fetch_API/Using_Fetch
var app = new Vue({
	el: '#app',
	data: {
		file: '',
		message: 'cargue su archivo txt'
	},
	methods: {
		handleFileUpload() {
			this.file = this.$refs.file.files[0];
		},
		submitFile(){
			let formData = new FormData();
            formData.append('file', this.file);
			console.log('item ', formData )
		}
	}
});