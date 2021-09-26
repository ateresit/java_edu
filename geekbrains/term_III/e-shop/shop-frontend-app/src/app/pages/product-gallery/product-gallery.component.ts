import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../services/product.service";
import {Product} from "../../model/product";

export const PRODUCT_GALLERY_URL = 'product';

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.scss']
})

export class ProductGalleryComponent implements OnInit {

  products: Product[] = [];
  isError: boolean = false;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.retrieveProducts()
  }

  private retrieveProducts() {
    this.productService.findAll()
      .then(res => {
        this.products = res.content;
      })
      .catch(err => {
        console.error(err);
        this.isError = true;
      })
  }

}
