import {Category} from "./category";

export class Product {

  constructor(public id: number,
              public title: string,
              public description: string,
              public cost: number,
              public category: Category,
              public pictures: number[]) {
  }
}
