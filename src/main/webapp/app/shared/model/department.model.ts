import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IDepartment {
  id?: number;
  departmentID?: string;
  departmentName?: string;
  restaurants?: IRestaurant[];
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentID?: string, public departmentName?: string, public restaurants?: IRestaurant[]) {}
}
