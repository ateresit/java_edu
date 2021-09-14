import {Product} from "./product";
import {Pageable} from "./pageable";

export class Page {

  constructor(public content: Product,
              public pageable: Pageable,
              public last: boolean,
              public totalPages: number,
              public totalElements: number,
              public numberOfElements: number,
              public number: number,
              public size: number,
              public first: boolean,
              public empty: boolean) {
  }

}
