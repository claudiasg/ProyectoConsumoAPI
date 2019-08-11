import { IPlate } from 'app/shared/model/plate.model';
import { IDrink } from 'app/shared/model/drink.model';
import { IDessert } from 'app/shared/model/dessert.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface IRestaurant {
  id?: number;
  restaurantID?: string;
  restaurantName?: string;
  address?: string;
  plates?: IPlate[];
  drinks?: IDrink[];
  desserts?: IDessert[];
  departmentName?: IDepartment;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public restaurantID?: string,
    public restaurantName?: string,
    public address?: string,
    public plates?: IPlate[],
    public drinks?: IDrink[],
    public desserts?: IDessert[],
    public departmentName?: IDepartment
  ) {}
}
