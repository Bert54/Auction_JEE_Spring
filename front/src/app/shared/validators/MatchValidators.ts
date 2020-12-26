import {AbstractControl, ValidationErrors} from '@angular/forms';

export class MatchValidators {

  static mustMatch(c: AbstractControl): ValidationErrors  | null {
    if (c.get('password').value !== c.get('cpassword').value) {
      return { notSame: true };
    }
    return null;
  }

}
