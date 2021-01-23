import {AbstractControl, ValidationErrors} from '@angular/forms';

export class NewArticleDateValid {

  static isInTheFutur(c: AbstractControl): ValidationErrors | null {
    if (new Date(c.value).getTime() / 1000 <= Math.floor(Date.now() / 1000)) {
      return {inPast: true};
    }
    return null;
  }
}
