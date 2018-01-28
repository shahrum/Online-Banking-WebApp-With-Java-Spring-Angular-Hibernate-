import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';

@Injectable()
export class AppointmentService {

  constructor(private http: Http) { }

  getAppointmentList(){
  	let url = "http://localhost:9090/api/appointment/all";
  	return this.http.get(url,{withCredentials:true});
  }

  confirmAppointment(id: number){
  	let url = "http://localhost:9090/api/appointment/"+id+"/confirm";
  	return this.http.get(url,{withCredentials:true});
  }

}
