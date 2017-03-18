import {Injectable} from '@angular/core';
import {Http, RequestOptions, RequestOptionsArgs, URLSearchParams, Headers} from '@angular/http';
import {API_URL} from '../config';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class ImportService {
  constructor(private http:Http) {
    this.http = http;
  }


  public importFile(formData):Observable<any> {

    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    let options = new RequestOptions({ headers: headers });

    return this.http.post(API_URL + '/import', formData, options)
      .map(res => res.json())
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'))
  }


}
