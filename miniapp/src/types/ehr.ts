export type BindingState='UNBOUND'|'PENDING_CONFIRMATION'|'CORRECTION_PENDING'|'BOUND'
export interface Employee {employeeId:string;recordId:string;name:string;employeeNo:string;gender?:string|null;birthDate?:string|null;organization?:string|null;team?:string|null;position?:string|null;phoneMasked?:string|null;rowVersion?:number}
export interface MiniState {miniUserId?:string;bindingState:BindingState;nextAction?:string;canEnterHome?:boolean;needIdentityVerification?:boolean;employee?:Employee;consentVersion?:string}
export interface MiniLogin extends MiniState {miniUserId:string;isNewUser?:boolean;token:string;expiresIn:number;expiresAt:string;tokenType:string}
export interface LoginConfig {wechatConfigured:boolean;consentVersion:string;bindingRequired:boolean;loginTokenExpiresIn:number;verificationCodeLength:number}
export interface BindingChallenge {bindingState:'PENDING_CONFIRMATION';confirmationToken:string;expiresAt:string;employee:Employee;notice?:string}
