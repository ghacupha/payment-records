///
/// ERP System - ERP data management platform: Payment Records
/// Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU General Public License for more details.
///
/// You should have received a copy of the GNU General Public License
/// along with this program. If not, see <http://www.gnu.org/licenses/>.
///

export interface GuinessCompatibleSpy extends jasmine.Spy {
  /** By chaining the spy with and.returnValue, all calls to the function will return a specific
   * value. */
  andReturn(val: any): GuinessCompatibleSpy;
  /** By chaining the spy with and.callFake, all calls to the spy will delegate to the supplied
   * function. */
  andCallFake(fn: Function): GuinessCompatibleSpy;
  /** removes all recorded calls */
  reset(): void;
}

export class SpyObject {
  constructor(type?: any) {
    if (type) {
      Object.keys(type.prototype).forEach(prop => {
        let m = null;
        try {
          m = type.prototype[prop];
        } catch (e) {
          // As we are creating spys for abstract classes,
          // these classes might have getters that throw when they are accessed.
          // As we are only auto creating spys for methods, this
          // should not matter.
        }
        if (typeof m === 'function') {
          this.spy(prop);
        }
      });
    }
  }

  spy(name: string): GuinessCompatibleSpy {
    if (!this[name]) {
      this[name] = this.createGuinnessCompatibleSpy(name);
    }
    return this[name];
  }

  private createGuinnessCompatibleSpy(name: string): GuinessCompatibleSpy {
    const newSpy: GuinessCompatibleSpy = jasmine.createSpy(name) as any;
    newSpy.andCallFake = newSpy.and.callFake as any;
    newSpy.andReturn = newSpy.and.returnValue as any;
    newSpy.reset = newSpy.calls.reset as any;
    // revisit return null here (previously needed for rtts_assert).
    newSpy.and.returnValue(null);
    return newSpy;
  }
}
