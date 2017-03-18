import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'replaceletter'})
export class ReplaceletterPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return value;
    return value.replace(/[:]+/g, '');
  }
}

let typeCache: { [label: string]: boolean } = {};
export function type<T>(label: T | ''): T {
  if (typeCache[<string>label]) {
    throw new Error(`Action type "${label}" is not unqiue"`);
  }

  typeCache[<string>label] = true;

  return <T>label;
}
