import * as React from "react";
import {Component, FormEvent} from "react";

import "./Signup.css"
import * as Cookies from "js-cookie";

interface SignupState {
    freeNickname: boolean;
}

export default class Signup extends Component<{}, SignupState> {

    private static NICKNAME_CHECK_URL: string = "/search/exists/";
    private static SUBMIT_URL: string = "/signup/save";

    private nicknameInput: HTMLInputElement;
    private passwordInput: HTMLInputElement;
    private repeatPasswordInput: HTMLInputElement;
    private csrfToken: string;

    constructor(props: {}, context: any) {
        super(props, context);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
        this.state = {
            freeNickname: true
        }
    }

    render(): React.ReactNode {
        return (
            <form action={Signup.SUBMIT_URL} method="post" className="signupForm"
                  onSubmit={(e: FormEvent<any>) => this.verify(e)}>
                <div className="signupHeader">
                    <h3>Create new photogramm user</h3>
                </div>
                <div className="signupContent">
                    <label><b>Nickname</b></label>
                    <input className="signupInput" ref={(e) => {this.nicknameInput = e}}
                           type="text" placeholder="Enter nickname"
                           name="nickname" onChange={(e) => {this.verifyNickname()}} required/>

                    <label><b>Email</b></label>
                    <input className="signupInput" type="text" placeholder="Enter email"
                           name="email" required/>

                    <label><b>Name</b></label>
                    <input className="signupInput" type="text" placeholder="Enter name" name="name"
                           required/>

                    <label><b>Password</b></label>
                    <input className="signupInput" type="password"
                           ref={(e) => this.passwordInput = e} placeholder="Enter Password"
                           name="password" required/>

                    <label><b>Repeat Password</b></label>
                    <input className="signupInput" type="password"
                           ref={(e) => this.repeatPasswordInput = e} placeholder="Repeat Password"
                           name="psw-repeat"
                           required/>

                    <div className="clearfix">
                        <button type="submit" className="signupButton" disabled={!this.state.freeNickname}>Sign Up</button>
                    </div>

                    <input type="hidden" name="_csrf" value={this.csrfToken}/>
                </div>
            </form>
        );
    }

    private verify(e: FormEvent<any>) {
        if (this.passwordInput.value != this.repeatPasswordInput.value) {
            alert("Input passwords are not matched!");
            e.preventDefault();
            return;
        }
    }

    private verifyNickname() {
       const nickname = this.nicknameInput.value;
       if (nickname == "") {
           this.setState({
               freeNickname: true
           });
           return;
       }

       fetch(Signup.NICKNAME_CHECK_URL + nickname, {
           credentials: "same-origin"
       }).then((r) => {
           if (r.status != 200) {
               this.setState({
                   freeNickname: false
               });
               return;
           }
           r.json().then((exists: boolean) => {
               this.setState({
                   freeNickname: !exists
               });
           })
       }).catch((e) => {
           this.setState({
               freeNickname: false
           });
       })
    }
}