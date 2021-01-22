import {AbstractControl, ValidationErrors} from '@angular/forms';

export class AdressValidator {

  static adressComplete(c: AbstractControl): ValidationErrors  | null {
    if (c.get('street').value !== '' || c.get('city').value !== '' || c.get('postcode').value !== '') {
      if (c.get('street').value === '' || c.get('city').value === '' || c.get('postcode').value === '') {
      return { empty: true };
      }
    }
    return null;
  }

}
