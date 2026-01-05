<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\User;

class UserAdminController extends Controller
{
    public function index()
    {
        $clients = User::orderBy('id', 'desc')->paginate(20);
        return view('admin.clients.index', compact('clients'));
    }
}


