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
			// formData.append('filetxt', this.file);
			
			

			//console.log('logs ', formData ); // key1 = value1, then key2 = value2
		


			//call api
			/*fetch('http://localhost:8080/upload/file', {
				method: 'POST',
				headers: {
					'Content-Type': 'multipart/form-data',
				},
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					console.log('Success:', data);
				})*/



		}
	}
});