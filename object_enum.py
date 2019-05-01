def obj_name_to_int(string):
    name_number = {
        'door':1, 'doorHandle':2
    }
    try:
        value = name_number[string]
        return value
    except:
        print('invalid object name')