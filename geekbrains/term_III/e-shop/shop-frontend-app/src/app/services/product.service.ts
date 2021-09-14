import { Injectable } from '@angular/core';
import {Product} from "../model/product";
import {HttpClient} from "@angular/common/http";
import {Page} from "../model/page";

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  private products: Product[] = [];

  constructor(private http: HttpClient) { }

  public findAll() {
    return this.http.get<Page>('/api/v1/product/all').toPromise();
  }

}
