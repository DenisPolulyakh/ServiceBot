export interface IDictionaryList {
  id:number;
  tags:any;
  message:string;
  isEdit:boolean;
  isNew:boolean;
}

export class DictionaryList {

  private _id:number;
  private _tags:any;
  private _message:string;
  private _isEdit:boolean;
  private _isNew:boolean;

  constructor(dto?:any) {
    if (dto) this.deserialize(dto);
  }

  getTags():any {
    return this._tags;
  }

  setTags(value:any) {
    this._tags = value;
  }

  getMessage():string {
    return this._message;
  }

  setMessage(value:string) {
    this._message = value;
  }

  getId():any {
    return this._id;
  }

  setId(value:any) {
    this._id = value;
  }

  getIsEdit():boolean {
    return this._isEdit;
  }

  setIsEdit(value:boolean) {
    this._isEdit = value;
  }


  getIsNew():boolean {
    return this._isNew;
  }

  setIsNew(value:boolean) {
    this._isNew = value;
  }

  public deserialize(data:any):DictionaryList {
    this.setTags(data.tags);
    this.setMessage(data.message);
    this.setId(data.id);
    this.setIsEdit(data.isEdit ? true : false);
    this.setIsNew(data.isNew ? true : false);

    return this;
  }
}
