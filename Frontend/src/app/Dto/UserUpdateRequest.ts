export interface UserUpdateRequest {
    id: number;
  username: string;
  email: string;
  password?: string;
  role?: string;
}
