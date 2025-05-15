import { ToastrModule, GlobalConfig } from 'ngx-toastr';

export const toastrConfig: Partial<GlobalConfig> = {
  timeOut: 3000,
  positionClass: 'toast-top-right',
  preventDuplicates: true,
  progressBar: true,
  closeButton: true,
  newestOnTop: true,
  enableHtml: true,
  easing: 'ease-in',
  easeTime: 300,
  extendedTimeOut: 1000,
  progressAnimation: 'decreasing' as const,
  tapToDismiss: true,
  titleClass: 'toast-title',
  messageClass: 'toast-message',
  iconClasses: {
    error: 'toast-error',
    info: 'toast-info',
    success: 'toast-success',
    warning: 'toast-warning',
  },
};
