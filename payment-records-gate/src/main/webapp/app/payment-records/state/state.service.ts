import {BehaviorSubject, Observable} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";

/**
 * Generic state management until I have the time to set up state management with
 * NGRX
 */
export class StateService<T> {

  private state$: BehaviorSubject<T>;

  protected get state(): T {
    return this.state$.getValue();
  }

  constructor(initialState: T) {
    this.state$ = new BehaviorSubject<T>(initialState);
  }

  protected select<K>(mapFn: (state: T) => K): Observable<K> {
    return this.state$.asObservable().pipe(
      map((state: T) => mapFn(state)),
      distinctUntilChanged()
    );
  }

  /**
   *
   * @param newState This is a partial allowing us to pass only some part of the bigger
   * state
   * @protected
   */
  protected setState(newState: Partial<T>): void {
    this.state$.next({
      ...this.state,
      ...newState,
    });
  }
}
