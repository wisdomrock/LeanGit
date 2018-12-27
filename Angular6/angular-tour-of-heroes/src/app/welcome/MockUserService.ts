import {UserService} from './UserService';

export class MockUserService extends UserService{
    isLoggedIn = true;
    user = { name: 'Test User'};
}