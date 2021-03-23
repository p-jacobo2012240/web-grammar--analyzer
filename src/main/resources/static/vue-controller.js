// https://developer.mozilla.org/es/docs/Web/API/Fetch_API/Using_Fetch
// https://serversideup.net/uploading-files-vuejs-axios/
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
		submitFile() {
			let formData = new FormData();
			formData.append('filetxt', this.file);

			//send data into file
			axios.post('http://localhost:8080/upload/file',
				formData,
				{
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				}
			).then(function() {
				console.log('SUCCESS!!');
			})
				.catch(function() {
					console.log('FAILURE!!');
				});

		}
	}
});