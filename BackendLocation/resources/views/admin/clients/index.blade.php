@extends('admin.layout')

@section('title', 'Admin - Clients')

@section('content')
    <h1 class="h3 mb-3">Clients</h1>

    <div class="card">
        <div class="table-responsive">
            <table class="table table-striped mb-0">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Créé le</th>
                </tr>
                </thead>
                <tbody>
                @foreach($clients as $u)
                    <tr>
                        <td>{{ $u->id }}</td>
                        <td>{{ $u->name }}</td>
                        <td>{{ $u->email }}</td>
                        <td>{{ $u->created_at }}</td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3">
        {{ $clients->links() }}
    </div>
@endsection


