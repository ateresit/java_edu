import { Injectable } from '@angular/core';
import {Product} from "./product";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private identity: number = 6;

  private products: { [key: number]: Product } = {
    1: new Product(1, 'First Product', 18),
    2: new Product(2, 'Second Product', 27),
    3: new Product(3, 'Third Product', 34),
    4: new Product(4, 'Fourth Product', 52),
    5: new Product(5, 'Fifth Product', 21),
  };

  constructor(public http: HttpClient) { }

  public findAll() {
    return this.http.get<Product[]>('/api/v1/product/all').toPromise();
  // return new Promise<Product[]>((resolve, reject) =>
  // {
  //   resolve(
  //     Object.values(this.products)
  //   )
  // })
  }

  public findById(id: number) {
    return this.http.get<Product>(`/api/v1/product/${id}`).toPromise();
    // return new Promise<User>((resolve, reject) =>
    // {
    //   resolve(
    //     this.products[id]
    //   )
    // })
  }

  public save(product: Product) {
    if (product.id == null) {
      return this.http.post('/api/v1/product', product).toPromise()
    } else {
      return this.http.put('/api/v1/product', product).toPromise()
    }
    // return new Promise<void>((resolve, reject) => {
    //   if (product.id == null) {
    //     product.id = this.identity++;
    //   }
    //   this.products[product.id] = product;
    //   resolve();
    // })
  }

  public delete(id: number) {
    return this.http.delete<Product>(`/api/v1/product/${id}`).toPromise();
    // return new Promise<void>((resolve, reject) => {
    //   delete this.products[id];
    //   resolve();
    // })
  }

}
