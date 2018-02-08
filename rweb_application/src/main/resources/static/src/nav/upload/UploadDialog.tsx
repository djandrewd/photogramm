import * as React from "react";
import {Component} from "react";
import * as ReactModal from "react-modal";

import "./UploadDialog.css";
import * as Cookies from "js-cookie";

interface UploadProperties {
    isOpen: boolean;

    onClose(): () => void

    onUpload(): () => void
}


export default class UploadDialog extends Component<UploadProperties, {}> {

    private imageDescription: HTMLTextAreaElement;
    private fileInput: HTMLInputElement;
    private csrfToken: string;

    constructor(props: UploadProperties) {
        super(props);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        return (
            <div className="uploadDialog">
                <ReactModal
                    isOpen={this.props.isOpen}
                    onRequestClose={this.props.onClose}
                    contentLabel="Upload new image"
                    className='uploadContent'
                >
                    <div className="uploadContainer">
                        <label className="uploadLabel">Image description</label>
                        <textarea className="uploadImageText"
                                  ref={(e) => {
                                      this.imageDescription = e;
                                  }}
                                  placeholder="Image description"
                                  required
                        />
                        <input type="file" accept="image/*"
                               className="btn btn-default btnUploadFile"
                               ref={(e) => {
                                   this.fileInput = e;
                               }}
                               required
                        />
                        <input type="submit" value="Upload file"
                               className="btn btn-default btnUploadSubmit"
                               onClick={(e) => this.submitImage()}/>
                    </div>
                </ReactModal>
            </div>
        );
    }

    private submitImage() {
        const imageDesc = this.imageDescription.value;
        if (imageDesc === '') {
            alert('FeedImage description cannot be empty!');
            return;
        }
        const files: FileList = this.fileInput.files;
        if (files == null || files.length == 0) {
            alert('FeedImage file must be selected!');
            return;
        }
        const imageFile = files[0];
        const form = new FormData();
        form.append('file', imageFile, imageFile.name);
        form.append('name', imageDesc);

        fetch("/upload/img", {
            method: 'POST',
            headers: new Headers({
                'X-XSRF-TOKEN': this.csrfToken
            }),
            body: form,
            credentials: "same-origin"
        }).then((r) => {
            this.props.onUpload();
        }).catch((e) => {
            console.log("Error occurred: " + e);
            this.props.onClose();
        })
    }


}
